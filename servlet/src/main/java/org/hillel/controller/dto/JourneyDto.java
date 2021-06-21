package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.persistence.entity.VehicleEntity;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class JourneyDto {

    private Long id;
    private String name;
    private String stationFrom;
    private String stationTo;
    private String dateFrom ;
    private String dateTo;
    private VehicleDto vehicle;
}
