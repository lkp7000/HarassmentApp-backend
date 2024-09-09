package com.map.harass.mapper;

import com.map.harass.entity.Intervention;
import com.map.harass.dto.InterventionRequestDTO;
import com.map.harass.dto.InterventionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InterventionMapper {


        InterventionMapper questionMapper = Mappers.getMapper(InterventionMapper.class);

        Intervention interventionRequestDtoTOIntervention(InterventionRequestDTO intervention);
        InterventionResponseDTO interventionTOInterventionResponseDto(Intervention intervention);


}
