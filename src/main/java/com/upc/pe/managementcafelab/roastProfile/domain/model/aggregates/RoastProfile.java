package com.upc.pe.managementcafelab.roastProfile.domain.model.aggregates;

import com.upc.pe.managementcafelab.roastProfile.domain.model.commands.CreateRoastProfileCommand;
import com.upc.pe.managementcafelab.roastProfile.domain.model.commands.UpdateRoastProfileCommand;
import com.upc.pe.managementcafelab.roastProfile.domain.model.valueObjects.DurationInSeconds;
import com.upc.pe.managementcafelab.roastProfile.domain.model.valueObjects.RoastType;
import com.upc.pe.managementcafelab.roastProfile.domain.model.valueObjects.TemperatureRange;
import com.upc.pe.managementcafelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RoastProfile extends AuditableAbstractAggregateRoot<RoastProfile> {

    @Column(name = "coffee_lot_id", nullable = false)
    private Long coffeeLotId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "temperature_start", nullable = false)),
            @AttributeOverride(name = "end", column = @Column(name = "temperature_end", nullable = false))
    })
    private TemperatureRange temperatureRange;

    @Embedded
    private DurationInSeconds durationSeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "roast_type", nullable = false)
    private RoastType type;

    @Column(name = "is_Favorite", nullable = false)
    private Boolean isFavorite;

    @Column(name = "acidity", nullable = false)
    private Integer acidity;

    @Column(name = "sweetness", nullable = false)
    private Integer sweetness;

    @Column(name = "body", nullable = false)
    private Integer body;

    public RoastProfile() {
    }
    public RoastProfile(
        Long coffeeLotId,
        Long userId,
        String name,
        TemperatureRange temperatureRange,
        DurationInSeconds durationSeconds,
        RoastType type,
        Boolean isFavorite,
        Integer acidity,
        Integer sweetness,
        Integer body
)
{
    this.coffeeLotId = coffeeLotId;
    this.userId = userId;
    this.name = name;
    this.temperatureRange = temperatureRange;
    this.durationSeconds = durationSeconds;
    this.type = type;
    this.isFavorite = isFavorite;

    this.acidity = acidity;
    this.sweetness = sweetness;
    this.body = body;
}

public RoastProfile(CreateRoastProfileCommand command) {
    this(
            command.coffeeLotId(),
            command.userId(),
            command.name(),
            new TemperatureRange(
                    command.temperatureStart(),
                    command.temperatureEnd()
            ),
            new DurationInSeconds(command.durationSeconds()),
            RoastType.valueOf(command.type().toUpperCase()),
            command.isFavorite(),

            command.acidity(),
            command.sweetness(),
            command.body()
        );
    }

    public void update(UpdateRoastProfileCommand command) {
        this.name = command.name();
        this.temperatureRange = new TemperatureRange(command.temperatureStart(), command.temperatureEnd());
        this.durationSeconds = new DurationInSeconds(command.durationSeconds());
        this.type = RoastType.valueOf(command.type().toUpperCase());
        this.isFavorite = command.isFavorite();
        this.type = RoastType.valueOf(command.type().toUpperCase());
        this.acidity = command.acidity();
        this.sweetness = command.sweetness();
        this.body = command.body();

    }
}