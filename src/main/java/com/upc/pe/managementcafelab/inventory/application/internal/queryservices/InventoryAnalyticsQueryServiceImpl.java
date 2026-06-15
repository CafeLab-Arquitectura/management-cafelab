package com.upc.pe.managementcafelab.inventory.application.internal.queryservices;


import com.upc.pe.managementcafelab.coffee.insfrastructure.persistence.jpa.repositories.CoffeeLotRepository;
import com.upc.pe.managementcafelab.inventory.domain.model.aggregates.InventoryEntry;
import com.upc.pe.managementcafelab.inventory.domain.model.queries.GetCoffeeLotAnalyticsQuery;
import com.upc.pe.managementcafelab.inventory.domain.model.aggregates.CoffeeLotAnalytics;
import com.upc.pe.managementcafelab.coffee.domain.model.aggregates.CoffeeLot;
import com.upc.pe.managementcafelab.inventory.domain.model.aggregates.MonthlyUsage;
import com.upc.pe.managementcafelab.inventory.domain.services.InventoryAnalyticsQueryService;
import com.upc.pe.managementcafelab.inventory.infrastructure.persistence.jpa.repositories.InventoryEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InventoryAnalyticsQueryServiceImpl
        implements InventoryAnalyticsQueryService {

    private final InventoryEntryRepository inventoryEntryRepository;
    private final CoffeeLotRepository coffeeLotRepository;

    public InventoryAnalyticsQueryServiceImpl(
            InventoryEntryRepository inventoryEntryRepository,
            CoffeeLotRepository coffeeLotRepository
    ) {
        this.inventoryEntryRepository = inventoryEntryRepository;
        this.coffeeLotRepository = coffeeLotRepository;
    }

    @Override
    public List<CoffeeLotAnalytics> handle(
            GetCoffeeLotAnalyticsQuery query
    ) {

        var entries = inventoryEntryRepository
                .findByUserIdOrderByDateUsedDesc(query.userId());

        var lots = coffeeLotRepository
                .findByUserId(query.userId());

        Map<Long, CoffeeLot> lotsById = lots.stream()
                .collect(Collectors.toMap(
                        CoffeeLot::getId,
                        Function.identity()
                ));

        Map<Long, List<InventoryEntry>> entriesByLot =
                entries.stream()
                        .collect(Collectors.groupingBy(
                                InventoryEntry::getCoffeeLotId
                        ));

        int currentYear = Year.now().getValue();

        List<CoffeeLotAnalytics> analytics = new ArrayList<>();

        for (var lotGroup : entriesByLot.entrySet()) {

            Long coffeeLotId = lotGroup.getKey();
            List<InventoryEntry> lotEntries = lotGroup.getValue();

            double totalQuantityUsed = lotEntries.stream()
                    .mapToDouble(InventoryEntry::getQuantityUsed)
                    .sum();

            long totalProductsGenerated = lotEntries.size();

            LocalDateTime firstUsageDate = lotEntries.stream()
                    .map(InventoryEntry::getDateUsed)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            LocalDateTime lastUsageDate = lotEntries.stream()
                    .map(InventoryEntry::getDateUsed)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            Map<Integer, Double> monthlyTotals =
                    IntStream.rangeClosed(1, 12)
                            .boxed()
                            .collect(Collectors.toMap(
                                    month -> month,
                                    month -> 0.0
                            ));

            lotEntries.stream()
                    .filter(entry ->
                            entry.getDateUsed().getYear() == currentYear)
                    .forEach(entry -> {

                        int month = entry.getDateUsed().getMonthValue();

                        monthlyTotals.put(
                                month,
                                monthlyTotals.get(month)
                                        + entry.getQuantityUsed()
                        );
                    });

            List<MonthlyUsage> monthlyUsage =
                    IntStream.rangeClosed(1, 12)
                            .mapToObj(month ->
                                    new MonthlyUsage(
                                            month,
                                            monthlyTotals.get(month)
                                    ))
                            .toList();

            String lotName = Optional.ofNullable(
                            lotsById.get(coffeeLotId))
                    .map(CoffeeLot::getLotName)
                    .orElse("Unknown Lot");

            analytics.add(
                    new CoffeeLotAnalytics(
                            coffeeLotId,
                            lotName,
                            totalQuantityUsed,
                            totalProductsGenerated,
                            firstUsageDate,
                            lastUsageDate,
                            monthlyUsage
                    )
            );
        }

        return analytics;
    }
}