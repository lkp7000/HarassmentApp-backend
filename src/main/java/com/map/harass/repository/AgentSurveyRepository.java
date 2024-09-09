package com.map.harass.repository;

import com.map.harass.entity.Agent;
import com.map.harass.entity.AgentSurvey;
import com.map.harass.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentSurveyRepository extends JpaRepository<AgentSurvey, Long> {

    AgentSurvey findBySurveyID(Survey survey);

    List<AgentSurvey> findByAgentID(Agent agentID);
}
