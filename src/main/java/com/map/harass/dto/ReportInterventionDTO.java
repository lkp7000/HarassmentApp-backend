package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportInterventionDTO {

    private String latitude;
    private String longitude;
    private LocalDate date;
    private Time time;
    private String location;
    private String category;
    private String description;
    private String type;
}
