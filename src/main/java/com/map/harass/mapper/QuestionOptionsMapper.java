package com.map.harass.mapper;

import com.map.harass.entity.QuestionOptions;
import com.map.harass.dto.QuestionOptionsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionOptionsMapper {

    QuestionOptionsMapper questionMapper = Mappers.getMapper(QuestionOptionsMapper.class);

    @Mapping(source = "questions.questionID", target = "questionID")
    QuestionOptionsDTO questionOptionsToQuestionOptionsDto(QuestionOptions questionOptions);

    List<QuestionOptionsDTO> questionListToQuestionDtoList(List<QuestionOptions> questionOptions);

    QuestionOptions questionOptionsDtoToQuestionOptions(QuestionOptionsDTO questionOptionsDTO);
}
