package com.upc.pe.managementcafelab.roastProfile.domain.model.valueObjects;

import jakarta.persistence.Embeddable;


@Embeddable
public record TemperatureRange(Double start, Double end) {
    public TemperatureRange {
            if (end <= start) {
                throw new IllegalArgumentException("El rango de temperatura final no debe ser menor o igual al del comienzo");
    }}



    public TemperatureRange() {
            this(null, null);
        }


}
