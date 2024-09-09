package com.map.harass.mapper;

import com.map.harass.entity.HelpCenter;
import com.map.harass.dto.HelpCenterDTO;
import com.map.harass.dto.HelpCenterFilterResposeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HelpCenterMapper {

    HelpCenterMapper questionMapper = Mappers.getMapper(HelpCenterMapper.class);

    @Mapping(source = "organisationName", target = "organisation_Name")
    @Mapping(source = "phoneNumber", target = "phone_Number")
    @Mapping(source = "organisationType", target = "organisation_Type")
    HelpCenter helpCenterDtoTOHelpCenter(HelpCenterDTO helpCenterDTO);

    @Mapping(source = "organisation_Name", target = "organisationName")
    @Mapping(source = "phone_Number", target = "phoneNumber")
    @Mapping(source = "organisation_Type", target = "organisationType")
    HelpCenterDTO helpCenterTOHelpCenterDto(HelpCenter helpCenter);

    List<HelpCenter> helpCenterDtoListTOHelpCenterList(List<HelpCenterDTO> helpCenterDTOList);
    List<HelpCenterDTO> helpCenterListTOHelpCenterDtoList(List<HelpCenter> helpCenterList);

    List<HelpCenterFilterResposeDTO> helpCenterDtoListToFilterResponseDtoList(List<HelpCenterDTO> helpCenterDTOList);

    HelpCenterFilterResposeDTO helpCenterDtoToFilterResponseDto(HelpCenterDTO helpCenterDTO);
}
