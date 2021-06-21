package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopAdditionalInfoDto {

    private Long id;
    private Double longitude;
    private Double latitude;
    private String city;
}
