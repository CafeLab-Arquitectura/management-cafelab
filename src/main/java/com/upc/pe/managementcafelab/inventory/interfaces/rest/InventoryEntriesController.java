package com.upc.pe.managementcafelab.inventory.interfaces.rest;

import com.upc.pe.managementcafelab.inventory.domain.model.queries.GetInventoryEntriesByUserIdQuery;
import com.upc.pe.managementcafelab.inventory.domain.services.InventoryEntryQueryService;
import com.upc.pe.managementcafelab.inventory.interfaces.rest.transform.InventoryEntryResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.upc.pe.managementcafelab.inventory.domain.services.InventoryAnalyticsQueryService;
import com.upc.pe.managementcafelab.inventory.domain.model.queries.GetCoffeeLotAnalyticsQuery;
import com.upc.pe.managementcafelab.inventory.interfaces.rest.transform.CoffeeLotAnalyticsResourceFromEntityAssembler;

import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/api/v1/inventory-entries",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Inventory Entries", description = "Stock consumption history endpoints")
public class InventoryEntriesController {

    private final InventoryEntryQueryService queryService;
    private final InventoryAnalyticsQueryService analyticsQueryService;

        public InventoryEntriesController(
                InventoryEntryQueryService queryService,
                InventoryAnalyticsQueryService analyticsQueryService
        ) {
            this.queryService = queryService;
            this.analyticsQueryService = analyticsQueryService;
        }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get inventory entries by user")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        var entries = queryService.handle(new GetInventoryEntriesByUserIdQuery(userId));

        var resources = entries.stream()
                .map(InventoryEntryResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/analytics/user/{userId}")
    @Operation(summary = "Get coffee lot analytics by user")
    public ResponseEntity<?> getAnalyticsByUserId(
            @PathVariable Long userId
    ) {

        var analytics = analyticsQueryService.handle(
                new GetCoffeeLotAnalyticsQuery(userId)
        );

        var resources = analytics.stream()
                .map(
                    CoffeeLotAnalyticsResourceFromEntityAssembler
                            ::toResourceFromEntity
                )
                .toList();

        return ResponseEntity.ok(resources);
    }
}
