package com.map.harass.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearWiseChartDTO {
     Map< Integer, Long> yearChart = new HashMap<>();
}
