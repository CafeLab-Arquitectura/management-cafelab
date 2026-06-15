package com.upc.pe.managementcafelab.inventory.interfaces.rest.resources;
public record MonthlyUsageResource(
    Integer month,
    Double quantityUsed
) {
}