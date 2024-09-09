package com.map.harass.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionAnswerMapper {


    QuestionAnswerMapper questionMapper= Mappers.getMapper(QuestionAnswerMapper.class);
//    QuestionAnswer answerDTOToQuestionAnswer(QuestionAnswerDTO answerDTO);
////    QuestionAnswerDTO questionAnswerToQuestionAnswerDTO( QuestionAnswer questionAnswer);
}
