package com.upc.pe.managementcafelab.inventory.domain.services;
import com.upc.pe.managementcafelab.inventory.domain.model.aggregates.CoffeeLotAnalytics;
import com.upc.pe.managementcafelab.inventory.domain.model.queries.GetCoffeeLotAnalyticsQuery;

import java.util.List;

public interface InventoryAnalyticsQueryService {

    List<CoffeeLotAnalytics> handle(
            GetCoffeeLotAnalyticsQuery query
    );
}