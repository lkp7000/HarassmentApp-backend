package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapDataDTO {

    private List<FilterAllInfoDTO> filterAllInfoDTOList;
    private ReportMapDTO reportMapDTO;
    Map< Integer, Long> yearChart  = new HashMap<>();
    Map< String, Long>      monthChart = new HashMap<>();
    Map< String, Long> dayChart = new HashMap<>();
}
