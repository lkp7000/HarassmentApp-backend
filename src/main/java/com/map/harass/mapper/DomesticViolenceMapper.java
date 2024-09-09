package com.map.harass.mapper;


import com.map.harass.entity.DomesticViolence;
import com.map.harass.dto.DomesticViolenceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DomesticViolenceMapper {


        com.map.harass.mapper.DomesticViolenceMapper DomesticViolenceMapper = Mappers.getMapper(com.map.harass.mapper.DomesticViolenceMapper.class);
        DomesticViolenceDTO domesticViolenceToDomesticViolenceDto(DomesticViolence domesticViolence);
        DomesticViolence domesticViolenceDtoToDomesticViolence(DomesticViolenceDTO domesticViolenceDTO);

}
