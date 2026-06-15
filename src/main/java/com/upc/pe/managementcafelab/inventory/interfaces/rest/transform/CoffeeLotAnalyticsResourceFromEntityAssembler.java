package com.upc.pe.managementcafelab.inventory.interfaces.rest.transform;
import com.upc.pe.managementcafelab.inventory.domain.model.aggregates.CoffeeLotAnalytics;
import com.upc.pe.managementcafelab.inventory.interfaces.rest.resources.CoffeeLotAnalyticsResource;
import com.upc.pe.managementcafelab.inventory.interfaces.rest.resources.MonthlyUsageResource;

public class CoffeeLotAnalyticsResourceFromEntityAssembler {

    public static CoffeeLotAnalyticsResource toResourceFromEntity(
            CoffeeLotAnalytics entity
    ) {

        return new CoffeeLotAnalyticsResource(
                entity.coffeeLotId(),
                entity.coffeeLotName(),
                entity.totalQuantityUsed(),
                entity.totalProductsGenerated(),
                entity.firstUsageDate() != null
                        ? entity.firstUsageDate().toString()
                        : null,
                entity.lastUsageDate() != null
                        ? entity.lastUsageDate().toString()
                        : null,
                entity.monthlyUsage()
                        .stream()
                        .map(m -> new MonthlyUsageResource(
                                m.month(),
                                m.quantityUsed()
                        ))
                        .toList()
        );
    }
}