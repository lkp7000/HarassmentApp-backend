package com.map.harass.repository;

import com.map.harass.entity.AgentSurvey;
import com.map.harass.entity.QuestionAnswer;
import com.map.harass.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAnswerRepo extends JpaRepository<QuestionAnswer,Long> {
    List<QuestionAnswer> findAllByQuestionID(Questions questionID);

    List<QuestionAnswer> findByAgentSurveyID(AgentSurvey agentSurvey);

    QuestionAnswer findByAgentSurveyIDAndQuestionID(AgentSurvey agentSurvey, Questions questions);



}
