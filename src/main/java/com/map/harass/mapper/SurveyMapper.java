package com.map.harass.mapper;

import com.map.harass.entity.Survey;
import com.map.harass.dto.SurveyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    SurveyMapper questionMapper = Mappers.getMapper(SurveyMapper.class);

    SurveyDTO surveyToSurveyDTO(Survey survey);
    Survey surveyDtoToSurvey(SurveyDTO surveyDTO);
}
