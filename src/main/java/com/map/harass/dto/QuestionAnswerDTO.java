package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionAnswerDTO {

    private Long questionAnswerID;
    private Long selectedQuestionOptionID;
    private String questionAnswerText;
    private Long questionID;
    private Long agentSurveyID;

}