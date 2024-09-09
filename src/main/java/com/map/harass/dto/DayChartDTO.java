package com.map.harass.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayChartDTO {

     Map< String, Long> dayChart = new HashMap<>();

}
