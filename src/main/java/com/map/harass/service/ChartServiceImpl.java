package com.map.harass.service;

import com.map.harass.entity.DomesticViolence;
import com.map.harass.entity.Intervention;
import com.map.harass.repository.DomesticViolenceRepo;
import com.map.harass.repository.InterventionRepository;
import com.map.harass.repository.SurveyRepository;
import com.map.harass.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private FilterServiceImpl filterService;
    @Autowired
    private InterventionRepository interventionRepository;
    @Autowired
    private DomesticViolenceRepo   domesticViolenceRepo;

    @Override
    public YearChartDTO getAllChartData(MapKey mapKey) {
        MapDataDTO mapDataDTO =filterService.getFilterByVariousSelections(mapKey) ;
        YearChartDTO chartDTO = new YearChartDTO();
        if (mapKey.getRequestMap().containsKey("date") && "year".equals(mapKey.getRequestMap().get("date"))){
            Map<Integer, Long> surveyCountByYear = mapDataDTO.getFilterAllInfoDTOList().stream()
                    .collect(Collectors.groupingBy(
                            survey -> survey.getDate().getYear(),
                            Collectors.counting()
                    ));
            chartDTO.setYearChart(surveyCountByYear);
        }
        if (mapKey.getRequestMap().containsKey("date") && "month".equals(mapKey.getRequestMap().get("date"))){
            Map<String, Long> surveyCountByMonthYear = mapDataDTO.getFilterAllInfoDTOList().stream()
                    .collect(Collectors.groupingBy(
                            survey -> survey.getDate().format(DateTimeFormatter.ofPattern("MM-yyyy")),
                            Collectors.counting()
                    ));
            surveyCountByMonthYear.forEach((key, value) -> chartDTO.setMonthChart(surveyCountByMonthYear));

            chartDTO.setMonthChart(surveyCountByMonthYear);

        }
        if (mapKey.getRequestMap().containsKey("date") && "week".equals(mapKey.getRequestMap().get("date"))){
            Map<String, Long> surveyCountByWeekAndMonth = mapDataDTO.getFilterAllInfoDTOList().stream()
                    .collect(Collectors.groupingBy(
                            survey -> {
                                LocalDate date = survey.getDate();
                                int week = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                                return String.format("%d-W%d %d", date.getMonthValue(), week, date.getYear());
                            },
                            Collectors.counting()
                    ));
            chartDTO.setDayChart(surveyCountByWeekAndMonth);
        }

        return chartDTO;
    }



    @Override
    public MonthChartDTO getAllChartDataByMonth() {
        MonthChartDTO monthChartDTO=new MonthChartDTO();
        List<DomesticViolence> domesticViolenceSurveys = domesticViolenceRepo.findAll();
        List<Intervention> interventionSurveys = interventionRepository.findAll();
        Map<String, Long> surveyCountByMonthYear = domesticViolenceSurveys.stream()
                .filter(survey -> survey.getIncident_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> survey.getIncident_date().format(DateTimeFormatter.ofPattern("MM-yyyy")),
                        Collectors.counting()
                ));
        Map<String, Long> surveyCountByMonthYearintervention = interventionSurveys.stream()
                .filter(survey -> survey.getIntervention_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> survey.getIntervention_date().format(DateTimeFormatter.ofPattern("MM-yyyy")),
                        Collectors.counting()
                ));

        Map<String, Long> mergedMap = new HashMap<>(surveyCountByMonthYear);
        surveyCountByMonthYearintervention.forEach((key, value) ->
                mergedMap.merge(key, value, Long::sum));
        monthChartDTO.setMonthChart(mergedMap);
        return monthChartDTO;
 }

    @Override
    public DayChartDTO getAllChartDataByDay() {
        DayChartDTO dayChartDTO=new DayChartDTO();

        List<DomesticViolence> domesticViolenceSurveys = domesticViolenceRepo.findAll();
        List<Intervention> interventionSurveys = interventionRepository.findAll();
        Map<String, Long> surveyCountByWeekAndMonth = domesticViolenceSurveys.stream()
                .filter(survey -> survey.getIncident_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> {
                            LocalDate date = survey.getIncident_date();
                            int week = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                            return String.format("%d-W%d %d", date.getMonthValue(), week, date.getYear());
                        },
                        Collectors.counting()
                ));
        Map<String, Long> surveyCountByWeekAndMonthIntervention = interventionSurveys.stream()
                .filter(survey -> survey.getIntervention_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> {
                            LocalDate date = survey.getIntervention_date();
                            int week = date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                            return String.format("%d-W%d %d", date.getMonthValue(), week, date.getYear());
                        },
                        Collectors.counting()
                ));
        Map<String, Long> mergedMap = new HashMap<>(surveyCountByWeekAndMonth);
        surveyCountByWeekAndMonthIntervention.forEach((key, value) ->
                mergedMap.merge(key, value, Long::sum));
        dayChartDTO.setDayChart(mergedMap);
        return dayChartDTO;
    }
    @Override
    public YearWiseChartDTO getAllChartDataByYear() {

        YearWiseChartDTO yearChartDTO=new YearWiseChartDTO();
        List<DomesticViolence> domesticViolenceSurveys = domesticViolenceRepo.findAll();
        List<Intervention> interventionSurveys = interventionRepository.findAll();
        Map<Integer, Long> surveyCountByYear = domesticViolenceSurveys.stream()
                .filter(survey -> survey.getIncident_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> survey.getIncident_date().getYear(),
                        Collectors.counting()
                ));
        Map<Integer, Long> surveyCountByYearIntervention = interventionSurveys.stream()
                .filter(survey -> survey.getIntervention_date() != null)
                .collect(Collectors.groupingBy(
                        survey -> survey.getIntervention_date().getYear(),
                        Collectors.counting()
                ));

        Map<Integer, Long> mergedMap = new HashMap<>(surveyCountByYear);
        surveyCountByYearIntervention.forEach((key, value) ->
                mergedMap.merge(key, value, Long::sum));
        yearChartDTO.setYearChart(mergedMap);
        return yearChartDTO;
    }

}
