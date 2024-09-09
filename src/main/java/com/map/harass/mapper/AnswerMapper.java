package com.map.harass.mapper;

import com.map.harass.entity.Answers;
import com.map.harass.dto.AnswersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper questionMapper = Mappers.getMapper(AnswerMapper.class);

    AnswersDTO answerToAnswerDTO(Answers answers);
    Answers answersDtoToAnswers(AnswersDTO answersDTO);
}
