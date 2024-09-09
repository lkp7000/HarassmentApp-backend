package com.map.harass.mapper;

import com.map.harass.entity.Questions;
import com.map.harass.dto.QuestionsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionMapper questionMapper= Mappers.getMapper(QuestionMapper.class);

    QuestionsDTO questionToQuestionDTO(Questions questions);
    Questions questionDtoToQuestion(QuestionsDTO questionsDTO);
}
