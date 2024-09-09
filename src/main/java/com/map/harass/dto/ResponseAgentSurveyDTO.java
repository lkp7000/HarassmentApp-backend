package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseAgentSurveyDTO {

  private  List<ListAgentSurveyDTO> listAgentSurveyDTOS;
  private long totalSurvey;

}
