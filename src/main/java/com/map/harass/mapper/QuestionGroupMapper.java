package com.map.harass.mapper;

import com.map.harass.entity.QuestionGroup;
import com.map.harass.dto.QuestionGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionGroupMapper {

    QuestionGroupMapper questionMapper = Mappers.getMapper(QuestionGroupMapper.class);

    QuestionGroupDTO questionGroupListToQuestionGroupDtoList(QuestionGroup questionGroup);


    QuestionGroup questionGroupDtoToQuestionGroup(QuestionGroupDTO questionGroupDTO);
}
