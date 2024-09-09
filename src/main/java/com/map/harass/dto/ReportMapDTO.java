package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportMapDTO {

    private  List<ReportDomesticDTO> AllDomesticDTOList;
    private List<ReportInterventionDTO> reportInterventionDTOS;
}
