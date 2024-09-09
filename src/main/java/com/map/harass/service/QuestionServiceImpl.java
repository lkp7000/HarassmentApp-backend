package com.map.harass.service;

import com.map.harass.entity.Agent;
import com.map.harass.entity.AgentSurvey;
import com.map.harass.entity.Questions;
import com.map.harass.entity.Survey;
import com.map.harass.repository.*;
import com.map.harass.dto.QuestionsDTO;
import com.map.harass.mapper.QuestionGroupMapper;
import com.map.harass.mapper.QuestionMapper;
import com.map.harass.mapper.QuestionOptionsMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionOptionsRepository questionOptionsRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionOptionsMapper questionOptionsMapper;
    @Autowired
    private QuestionGroupMapper questionGroupMapper;
    @Autowired
    private AgentSurveyRepository agentSurveyRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Override
    @Transactional
    public List<QuestionsDTO> getallQuestions(Long agentID) {

        List<QuestionsDTO> questionsDTOs = new ArrayList<>();
        Optional<Agent> agentOptional = agentRepository.findById(agentID);

        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();

            Survey survey = createSurvey("Survey Text", agent);
            AgentSurvey agentSurvey = createAgentSurvey(agent, survey);

            List<Questions> questions = questionsRepository.findAll();
            for (Questions question : questions) {
                if ("active".equalsIgnoreCase(question.getStatus())) {
                    QuestionsDTO questionsDTO = mapQuestionsToDTO(question, survey);
                    questionsDTOs.add(questionsDTO);
                }
            }
        }

        return questionsDTOs;
    }


    private Survey createSurvey(String surveyDescription, Agent agent) {
        Survey survey = new Survey();
        survey.setSurveyDescription(surveyDescription);
        survey.setAgent(agent);
        return surveyRepository.save(survey);
    }


    private AgentSurvey createAgentSurvey(Agent agent, Survey survey) {
        AgentSurvey agentSurvey = new AgentSurvey();
        agentSurvey.setAgentID(agent);
        agentSurvey.setSurveyID(survey);
        return agentSurveyRepository.save(agentSurvey);
    }


    private QuestionsDTO mapQuestionsToDTO(Questions question, Survey survey) {
        QuestionsDTO questionsDTO = new QuestionsDTO();
        questionsDTO.setQuestionID(question.getQuestionID());
        questionsDTO.setQuestionText(question.getQuestionText());
        questionsDTO.setSurveyID(survey.getSurveyId());
        questionsDTO.setShortKey(question.getShortText());
        questionsDTO.setQuestionGroupID(questionGroupMapper.questionGroupListToQuestionGroupDtoList(question.getQuestionGroupID()));
        questionsDTO.setQuestionOptionsID(questionOptionsMapper.questionListToQuestionDtoList(question.getQuestionOptionsID()));
        return questionsDTO;
    }

    @Override
    public List<QuestionsDTO> addQuestionsForSurvey(QuestionsDTO questionsDTO) {
        questionMapper.questionDtoToQuestion(questionsDTO);
        Questions questionsEntity = questionMapper.questionDtoToQuestion(questionsDTO);
        questionsDTO.setStatus("ACTIVE");
        questionsRepository.save(questionsEntity);
        return Collections.singletonList(questionsDTO);
    }
}

