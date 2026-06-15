package com.upc.pe.managementcafelab.coffee.interfaces.rest.transform;


import com.upc.pe.managementcafelab.coffee.domain.model.aggregates.CoffeeLot;
import com.upc.pe.managementcafelab.coffee.domain.model.valueObjetcs.Certification;
import com.upc.pe.managementcafelab.coffee.interfaces.rest.resources.CoffeeLotResource;

import java.util.stream.Collectors;

public class CoffeeLotResourceFromEntityAssembler {

    public static CoffeeLotResource toResourceFromEntity(CoffeeLot entity) {

        return new CoffeeLotResource(
                entity.getId(),
                entity.getSupplierId(),
                entity.getUserId(),
                entity.getLotName(),
                entity.getCoffeeType().value(),
                entity.getOrigin(),
                entity.getAltitudeMeters(),
                entity.getStatus().value(),
                entity.getRemainingWeight(),
                entity.getProcessingMethod().value(),
                entity.getCertifications()
                        .stream()
                        .map(Certification::name)
                        .collect(Collectors.toList())
        );
    }
}