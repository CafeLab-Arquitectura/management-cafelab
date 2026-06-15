package com.upc.pe.managementcafelab.coffee.interfaces.rest.transform;

import com.upc.pe.managementcafelab.coffee.domain.model.commands.CreateCoffeeLotCommand;
import com.upc.pe.managementcafelab.coffee.domain.model.valueObjetcs.Certification;
import com.upc.pe.managementcafelab.coffee.interfaces.rest.resources.CreateCoffeeLotResource;

import java.util.List;

public class CreateCoffeeLotCommandFromResourceAssembler {

    public static CreateCoffeeLotCommand toCommand(CreateCoffeeLotResource resource) {

        return new CreateCoffeeLotCommand(
                resource.supplierId(),
                resource.userId(),
                resource.lotName(),
                resource.coffeeType(),
                resource.origin(),
                resource.status(),
                resource.altitudeMeters(),
                resource.processingMethod(),
                resource.initialWeight(),
                resource.certifications() == null
                        ? List.of()
                        : resource.certifications().stream()
                        .map(Certification::from)
                        .toList()
        );
    }
}