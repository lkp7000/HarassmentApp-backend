package com.map.harass.service;

import com.map.harass.dto.QuestionsDTO;

import java.util.List;

public interface QuestionService {

    List<QuestionsDTO> getallQuestions(Long agentID);

    List<QuestionsDTO> addQuestionsForSurvey(QuestionsDTO questionsDTO);
}
