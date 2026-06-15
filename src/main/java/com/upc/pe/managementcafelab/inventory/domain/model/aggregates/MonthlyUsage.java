package com.upc.pe.managementcafelab.inventory.domain.model.aggregates;
public record MonthlyUsage(
    Integer month,
    Double quantityUsed
) {
}