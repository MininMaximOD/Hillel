package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {

    private Long id;
    private String name;
    private String orderNumber;
    private int countSeats;
    private String vehicleStatus;
}
