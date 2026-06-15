package com.upc.pe.managementcafelab.inventory.interfaces.rest.resources;
import java.util.List;

public record CoffeeLotAnalyticsResource(
        Long coffeeLotId,
        String coffeeLotName,
        Double totalQuantityUsed,
        Long totalProductsGenerated,
        String firstUsageDate,
        String lastUsageDate,
        List<MonthlyUsageResource> monthlyUsage
) {
}