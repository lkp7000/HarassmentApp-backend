package com.map.harass.dto;

import com.map.harass.entity.Agent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class SurveyDTO {

        private Long surveyId;
        private String surveyDescription;
        private LocalDateTime lastwritten;
        private LocalDate     creationDate;
        private Agent agent;
}
