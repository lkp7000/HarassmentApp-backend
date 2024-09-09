package com.map.harass.service;

import com.map.harass.dto.*;

public interface ChartService {
    YearChartDTO getAllChartData(MapKey mapKey);
    YearWiseChartDTO getAllChartDataByYear();
    MonthChartDTO getAllChartDataByMonth();
    DayChartDTO getAllChartDataByDay();
}
