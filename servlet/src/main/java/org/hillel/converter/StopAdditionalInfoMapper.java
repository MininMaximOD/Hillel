package org.hillel.converter;


import org.hillel.controller.dto.StopAdditionalInfoDto;
import org.hillel.controller.dto.StopDto;
import org.hillel.persistence.entity.StopAdditionalInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StopAdditionalInfoMapper {

    StopAdditionalInfoDto stopAdditionalInfoToStopAdditionalInfoDto(StopAdditionalInfoEntity stop);

    StopAdditionalInfoEntity stopAdditionalInfoDtoToStopAdditionalInfo(StopAdditionalInfoDto dto);

}
