package com.map.harass.dto;

import com.map.harass.entity.Agent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class AgentSurveyDTO {

    private Long agentSurveyID;
    private Agent agentID;
    private SurveyDTO surveyDTOID;

}
