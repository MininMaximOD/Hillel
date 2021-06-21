package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopDto {

    private Long id;
    private String name;
    private String description;
    private Double longitude = 0.0;
    private Double latitude = 0.0;
    private String city = "";
}
