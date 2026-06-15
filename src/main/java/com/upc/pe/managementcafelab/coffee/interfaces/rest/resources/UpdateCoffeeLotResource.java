package com.upc.pe.managementcafelab.coffee.interfaces.rest.resources;

import java.util.List;

public record UpdateCoffeeLotResource(
        String lotName,
        Long supplierId,
        Long userId,
        String coffeeType,
        String origin,
        Double altitudeMeters,
        String processingMethod,
        List<String> certifications
) {
    public UpdateCoffeeLotResource {

        if (lotName == null || lotName.isBlank()) {
            throw new IllegalArgumentException("lotName is required");
        }

        if (coffeeType == null) {
            throw new IllegalArgumentException("coffeeType is required");
        }

        if (origin == null || origin.isBlank()) {
            throw new IllegalArgumentException("origin is required");
        }

        if (altitudeMeters == null || altitudeMeters < 0) {
            throw new IllegalArgumentException("altitudeMeters must be >= 0");
        }

        if (processingMethod == null) {
            throw new IllegalArgumentException("processingMethod is required");
        }
    }

}