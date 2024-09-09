package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAgentSurveyDTO {

        private Long  agentId;
        private String agentName;
        private Long  agentSurveyId;
        private String  Date;
}
