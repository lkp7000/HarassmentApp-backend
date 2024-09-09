package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionGroupDTO {

        private Long questionGroupId;
        private String questionGroupName;
        private SurveyDTO surveyDTO;
}
