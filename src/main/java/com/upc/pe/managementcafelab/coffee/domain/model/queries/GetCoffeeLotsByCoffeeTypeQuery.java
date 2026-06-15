package com.upc.pe.managementcafelab.coffee.domain.model.queries;

public record GetCoffeeLotsByCoffeeTypeQuery(String coffeeType) {
    public GetCoffeeLotsByCoffeeTypeQuery {
        if (coffeeType == null) {
            throw new IllegalArgumentException("coffeeType es requerido");
        }
    }
}