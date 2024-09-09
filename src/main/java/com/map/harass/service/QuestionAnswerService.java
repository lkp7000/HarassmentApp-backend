package com.map.harass.service;

import com.map.harass.dto.ResponseQuestionAnswerDTO;
import com.map.harass.dto.QuestionAnswerDTO;

import java.util.List;

public interface QuestionAnswerService {
   List<QuestionAnswerDTO> insertAnswers(List<QuestionAnswerDTO> questionAnswerDTO) ;

    List<QuestionAnswerDTO> getAllAnswers();

    List<QuestionAnswerDTO> getallQuestionsAnswerBySurveyID(Long agentsurveyID);

    List<ResponseQuestionAnswerDTO> getAllQuestionsAnswer(Long agentSurveyID);
}
