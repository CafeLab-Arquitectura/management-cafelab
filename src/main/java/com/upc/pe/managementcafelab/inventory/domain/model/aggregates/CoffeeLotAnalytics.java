package com.upc.pe.managementcafelab.inventory.domain.model.aggregates;
import java.time.LocalDateTime;
import java.util.List;

public record CoffeeLotAnalytics(
        Long coffeeLotId,
        String coffeeLotName,
        Double totalQuantityUsed,
        Long totalProductsGenerated,
        LocalDateTime firstUsageDate,
        LocalDateTime lastUsageDate,
        List<MonthlyUsage> monthlyUsage
) {
}