package com.map.harass.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterAllInfoDTO {
        private String    Longitude;
        private String        latitude;
        private LocalDate date;
        private String     time;
        private Long      surveyID;
        private Long agentSurveyID;
        private String addressName;
}
