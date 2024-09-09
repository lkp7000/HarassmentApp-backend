package com.map.harass.controller;

import com.map.harass.service.ChartService;
import com.map.harass.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/Chart")
@CrossOrigin("*")
public class ChartController {
    @Autowired
    ChartService chartService;

    @PostMapping("/chartfilter")
    public ResponseEntity<YearChartDTO> getAllChartData(@RequestBody MapKey mapKey){
        YearChartDTO chartDTO = chartService.getAllChartData(mapKey);
        return ResponseEntity.ok(chartDTO);
    }

    @GetMapping("/getAllByYear")
    public ResponseEntity<YearWiseChartDTO> getAllChartDataByYear(){
        YearWiseChartDTO chartDTO = chartService.getAllChartDataByYear();
        return ResponseEntity.ok(chartDTO);
    }
    @GetMapping("/getAllByMonth")
    public ResponseEntity<MonthChartDTO> getAllChartDataByMonth(){
        MonthChartDTO monthChartDTO = chartService.getAllChartDataByMonth();
        return ResponseEntity.ok(monthChartDTO);
    }
    @GetMapping("/getAllByDay")
    public ResponseEntity<DayChartDTO> getAllChartDataByDay(){
        DayChartDTO dayChartDTO = chartService.getAllChartDataByDay();
        return ResponseEntity.ok(dayChartDTO);
    }


}
