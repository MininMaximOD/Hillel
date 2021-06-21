package org.hillel.converter;

import org.hillel.controller.dto.VehicleDto;
import org.hillel.persistence.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper
public interface VehicleMapper {

    VehicleDto vehicleToVehicleDto(VehicleEntity vehicle);

    VehicleEntity vehicleDtoToVehicle(VehicleDto dto);

}
