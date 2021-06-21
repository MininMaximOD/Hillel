package org.hillel.converter;

import org.hillel.controller.dto.StopDto;
import org.hillel.persistence.entity.StopAdditionalInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StopMapper {

    @Mapping(target = "description", source = "commonInfo.description")
    @Mapping(target = "longitude", source = "additionalInfo.longitude")
    @Mapping(target = "latitude", source = "additionalInfo.latitude")
    @Mapping(target = "city", source = "additionalInfo.city")
//    @Mapping(target = "additionalInfoId", source = "additionalInfo.id")
    StopDto stopToStopDto(StopEntity stop);

    @Mapping(source = "description", target = "commonInfo.description")
    @Mapping(source = "longitude", target = "additionalInfo.longitude")
    @Mapping(source = "latitude", target = "additionalInfo.latitude")
    @Mapping(source = "city", target = "additionalInfo.city")
//    @Mapping(source = "additionalInfoId", target = "additionalInfo.id")
    StopEntity stopDtoToStop(StopDto dto);

}
