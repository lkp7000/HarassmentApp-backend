package com.map.harass.service;

import com.map.harass.entity.*;
import com.map.harass.repository.*;
import com.map.harass.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    @Autowired
    private QuestionAnswerRepo    questionAnswerRepo;
    @Autowired
    private AgentSurveyRepository agentSurveyRepo;
    @Autowired
    private SurveyRepository      surveyRepo;
    @Autowired
    private AddressRepository     addressRepository;
    @Autowired
    private InterventionRepository interventionRepository;
    @Autowired
    private DomesticViolenceRepo domesticViolenceRepo;


    @Override
    public MapDataDTO getFilterByVariousSelections(MapKey mapKey) {

        MapDataDTO mapDataDTO = new MapDataDTO();
        List<FilterAllInfoDTO> allInfoDTOList = new ArrayList<>();

        List<Survey> surveyList = surveyRepo.findAll();
        surveyList.forEach(survey -> processMapData(survey, allInfoDTOList));

        List<FilterAllInfoDTO> filteredDTOList = allInfoDTOList.stream()
                .filter(allInfoDTO -> {
                    AgentSurvey agentSurvey = agentSurveyRepo.findById(allInfoDTO.getAgentSurveyID()).orElse(null);
                    if (agentSurvey != null) {
                        List<QuestionAnswer> qAnsList = questionAnswerRepo.findByAgentSurveyID(agentSurvey);
                        return mapKey.getRequestMap().entrySet().stream().allMatch(entry -> {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            switch (key) {
                                case "date":
                                    return true;
                                case "personal_age":
                                    return personalAgeConditionsMet(value, qAnsList);
                                case "personal_attendedSchool":
                                    return personalAttendedSchool(value, qAnsList);
                                case "personal_violatedOnce":
                                    return personalViolatedOnce(value, qAnsList);
                                case "personal_married":
                                    return personalMarried(value, qAnsList);
                                case "personal_multiplePartners":
                                    return personalMultiplePartners(value, qAnsList);
                                case "personal_haveKids":
                                    return personalHaveKids(value, qAnsList);
                                case "employment_workingNow":
                                    return employmentWorkingNow(value, qAnsList);
                                case "employment_weeklyEarnings":
                                    return employmentWeeklyEarnings(value, qAnsList);
                                case "employment_streetVendor":
                                    return employmentStreetVendor(value, qAnsList);
                                case "employment_bankAccount":
                                    return employmentBankAccount(value, qAnsList);
                                case "employment_knowPartnerIncome":
                                    return employmentKnowPartnerIncome(value, qAnsList);
                                case "family_married":
                                    return familyMarried(value, qAnsList);
                                case "family_manviolent":
                                    return familyManviolent(value, qAnsList);
                                case "family_husbandEmployed":
                                    return familyHusbandEmployed(value, qAnsList);
                                case "family_childrenInSchool":
                                    return familyChildrenInSchool(value, qAnsList);
                                case "harasser_harassedBefore":
                                    return harasserHarassedBefore(value, qAnsList);
                                case "harasser_employed":
                                    return harasserEmployed(value, qAnsList);
                                case "harasser_hitWomanRegularly":
                                    return harasserHitWomanRegularly(value, qAnsList);
                                case "harasser_undergoneHIVTest":
                                    return harasserUndergoneHIVTest(value, qAnsList);
                                case "harasser_hivPositiveMedication":
                                    return harasserHivPositiveMedication(value, qAnsList);
                                case "health_jobLoss":
                                    return harasserJobLoss(value, qAnsList);
                                case "health_seekingResolution":
                                    return harasserSeekingResolution(value, qAnsList);
                                case "health_takenHIVTest":
                                    return healthTakenHIVTest(value, qAnsList);
                                case "health_birthControl":
                                    return healthBirthControl(value, qAnsList);
                                case "health_hivPositiveAndMedicated":
                                    return healthHIVPositiveAndMedicated(value, qAnsList);
                                case "health_abortion":
                                    return healthAbortion(value, qAnsList);
                                case "health_std":
                                    return healthStd(value, qAnsList);
                                case "dv_partnerHits":
                                    return dvPartnerHits(value, qAnsList);
                                case "dv_otherHits":
                                    return dvOtherHits(value, qAnsList);
                                case "dv_violate":
                                    return dvViolate(value, qAnsList);
                                case "dv_filedComplaint":
                                    return dvFiledComplaint(value, qAnsList);
                                default:
//                                    mapDataDTO.setFilterAllInfoDTOList();
                                    return true;
                            }
                        });
                    }
                    return false;
                })
                .collect(Collectors.toList());
         mapDataDTO.setFilterAllInfoDTOList(filteredDTOList);
        YearChartDTO yearChartDTO= processChartApi(mapDataDTO,mapKey);
        mapDataDTO.setYearChart(yearChartDTO.getYearChart());
        mapDataDTO.setMonthChart(yearChartDTO.getMonthChart());
        mapDataDTO.setDayChart(yearChartDTO.getDayChart());
        return mapDataDTO;
    }

    private YearChartDTO processChartApi(MapDataDTO mapDataDTO,MapKey mapKey){
        YearChartDTO chartDTO = new YearChartDTO();

            Map<Integer, Long> surveyCountByYear = mapDataDTO.getFilterAllInfoDTOList().stream()
                    .collect(Collectors.groupingBy(
                            survey -> survey.getDate().getYear(),
                            Collectors.counting()
                    ));
            chartDTO.setYearChart(surveyCountByYear);


            Map<String, Long> surveyCountByMonthYear = mapDataDTO.getFilterAllInfoDTOList().stream()
                    .collect(Collectors.groupingBy(
                            survey -> survey.getDate().format(DateTimeFormatter.ofPattern("MM-yyyy")),
                            Collectors.counting()
                    ));
            surveyCountByMonthYear.forEach((key, value) -> chartDTO.setMonthChart(surveyCountByMonthYear));

            chartDTO.setMonthChart(surveyCountByMonthYear);



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

        return chartDTO;
    }

    private void processMapData(Survey survey, List<FilterAllInfoDTO> filterAllInfoDTOList) {
        AgentSurvey agentSurvey = agentSurveyRepo
                .findBySurveyID(survey);

        if (agentSurvey != null&& agentSurvey.getLatitude()!=null) {
            FilterAllInfoDTO filterAllInfoDTO = new FilterAllInfoDTO();
            filterAllInfoDTO.setLatitude(agentSurvey.getLatitude());
            filterAllInfoDTO.setLongitude(agentSurvey.getLongitude());
            filterAllInfoDTO.setAddressName(agentSurvey.getFullAddress());
            if(agentSurvey.getCreationDate()!=null)
            {
                LocalDate Date = agentSurvey.getCreationDate().toLocalDate();
                filterAllInfoDTO.setDate(Date);
                LocalTime time = agentSurvey.getCreationDate().toLocalTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                String formattedTime = time.format(formatter);
                filterAllInfoDTO.setTime(formattedTime);
            }
            filterAllInfoDTO.setSurveyID(agentSurvey.getSurveyID().getSurveyId());
            filterAllInfoDTO.setAgentSurveyID(agentSurvey.getAgentSurveyID());
            filterAllInfoDTOList.add(filterAllInfoDTO);
        }
    }


    private boolean personalAgeConditionsMet(String age, List<QuestionAnswer> qAnsList) {

        String answer = "";
        for (QuestionAnswer qAns : qAnsList) {
            Questions questions = qAns.getQuestionID();
            if ("personal_age".equalsIgnoreCase(questions.getShortText())) {
                answer = qAns.getQuestionAnswerText();
                break;
            }
        }
        if (answer != null && answer.length() > 0) {
            int ageInt = Integer.parseInt(answer);
            if ("Under 18".equalsIgnoreCase(age)|| "Moins de 18 Ans".equalsIgnoreCase(age)) {
                return ageInt <= 18;
            } else if ("18-30".equalsIgnoreCase(age)|| "Entre 18 et 30 Ans".equalsIgnoreCase(age)) {
                return ageInt >= 18 && ageInt <= 30;
            } else if ("30-60".equalsIgnoreCase(age)|| "Entre 30 et 60 Ans".equalsIgnoreCase(age)) {
                return ageInt >= 30 && ageInt <= 60;
            } else if ("Over 60".equalsIgnoreCase(age)|| "Plus de 60 Ans".equalsIgnoreCase(age)) {
                return ageInt > 60;
            }
        }
        return false;
    }

    private boolean getAnswerAndEvaluate(String entry, List<QuestionAnswer> qAnsList, String questionShortText) {
        String value = entry;
        String answer = "";

        for (QuestionAnswer qAns : qAnsList) {
            Questions questions = qAns.getQuestionID();
            if (questions != null && questions.getShortText() != null) {
                if (questionShortText.equalsIgnoreCase(questions.getShortText())) {
                    answer = qAns.getQuestionAnswerText();
                    break;
                }
            }
        }

        return answer != null && answer.length() > 0 && getAnswerYesOrNo(answer, value);
    }

    private boolean personalAttendedSchool(String attendedSchool, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(attendedSchool, qAnsList, "personal_attendedSchool");
    }
    private boolean personalHaveKids(String attendedSchool, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(attendedSchool, qAnsList, "personal_haveKids");
    }
    private boolean personalViolatedOnce(String personalViolatedOnce, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(personalViolatedOnce, qAnsList, "personal_violatedOnce");
    }

    private boolean personalMarried(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "personal_married");
    }

    private boolean personalMultiplePartners(String personalMultiplePartners, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(personalMultiplePartners, qAnsList, "personal_multiplePartners");
    }

    private boolean employmentWorkingNow(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "employment_workingNow");
    }

    private boolean employmentStreetVendor(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "employment_streetVendor");
    }

    private boolean employmentBankAccount(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "employment_bankAccount");
    }

    private boolean employmentKnowPartnerIncome(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "employment_knowPartnerIncome");
    }

    private boolean employmentWeeklyEarnings(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "employment_weeklyEarnings");
    }

    private boolean familyMarried(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "family_married");
    }

    private boolean familyManviolent(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "family_manviolent");
    }

    private boolean familyHusbandEmployed(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "family_husbandEmployed");
    }

    private boolean familyChildrenInSchool(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "family_childrenInSchool");
    }

    private boolean harasserHarassedBefore(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_harassedBefore");
    }

    private boolean harasserEmployed(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_employed");
    }

    private boolean harasserHitWomanRegularly(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_hitWomanRegularly");
    }

    private boolean harasserUndergoneHIVTest(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_undergoneHIVTest");
    }

    private boolean harasserJobLoss(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_jobLoss");
    }

    private boolean harasserSeekingResolution(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_seekingResolution");
    }

    private boolean harasserWomanBefore(Map.Entry<String, String> entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry.getValue(), qAnsList, "harasser_womanBefore");
    }

    private boolean harasserHivPositiveMedication(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "harasser_hivPositiveMedication");
    }

    private boolean healthTakenHIVTest(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "health_takenHIVTest");
    }

    private boolean healthHIVPositiveAndMedicated(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "health_hivPositiveAndMedicated");
    }

    private boolean healthAbortion(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "health_abortion");
    }

    private boolean healthStd(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "health_std");
    }

    private boolean healthBirthControl(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "health_birthControl");
    }

    private boolean dvPartnerHits(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "dv_partnerHits");
    }

    private boolean dvOtherHits(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "dv_otherHits");
    }

    private boolean dvViolate(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "dv_violate");
    }

    private boolean dvFiledComplaint(String entry, List<QuestionAnswer> qAnsList) {
        return getAnswerAndEvaluate(entry, qAnsList, "dv_filedComplaint");
    }
    private boolean getAnswerYesOrNo(String answer, String Value) {
        String checkData = answer;
        if ("yes".equalsIgnoreCase(Value)||"Oui".equalsIgnoreCase(Value)) {
            if ("Yes".equalsIgnoreCase(checkData)) {
                return true;
            }
        }
        if ("no".equalsIgnoreCase(Value)||"Non".equalsIgnoreCase(Value)) {
            if ("No".equalsIgnoreCase(checkData)){
                return true;
            }
        }
        if("Once".equalsIgnoreCase(Value)||"Une fois".equalsIgnoreCase(Value)){
            if ("Once".equalsIgnoreCase(checkData)){
                return true;
            }
        }
        if("More than once".equalsIgnoreCase(Value)||"Plus d’une Fois".equalsIgnoreCase(Value)){
            if ("More than once".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("More then two".equalsIgnoreCase(Value)||"Plus de Deux".equalsIgnoreCase(Value)){
            if ("More than two".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("None".equalsIgnoreCase(Value)||"Aucun".equalsIgnoreCase(Value)){
            if ("None".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Unemployed".equalsIgnoreCase(Value)||"Au Chômage".equalsIgnoreCase(Value)){
            if ("Unemployed".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Employee".equalsIgnoreCase(Value)||"Employé".equalsIgnoreCase(Value)){
            if ("Employeed".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Prostitute".equalsIgnoreCase(Value)||"Prostituée".equalsIgnoreCase(Value)){
            if ("Prostitute".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Less than 1000 G Per Month".equalsIgnoreCase(Value)||"Moins de 1000 Gourdes par Mois".equalsIgnoreCase(Value)){
            if ("Less than 1000 G per Month".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Less than 10000 G Per Month".equalsIgnoreCase(Value)||"Moins de 10000 Gourdes par Mois".equalsIgnoreCase(Value)){
            if ("Less than 10000 G per Month".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Less than 45000 G Per Month".equalsIgnoreCase(Value)||"Moins de 45000 Gourdes par Mois".equalsIgnoreCase(Value)){
            if ("Less than 45000 G per Month".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("More than 45000 G Per Month".equalsIgnoreCase(Value)||"Plus de 45000 Gourdes par Mois".equalsIgnoreCase(Value)){
            if ("More than 45000 G per Month".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("More than 100000 G Per Month".equalsIgnoreCase(Value)||"Plus de 100000 Gourdes par Mois".equalsIgnoreCase(Value)){
            if ("More than 100000 G per Month".equalsIgnoreCase((checkData))){
                return true;
            }
        }

        if ("Unibanque".equalsIgnoreCase(Value)){
            if ("Unibanque".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Sogebanque".equalsIgnoreCase(Value)){
            if ("Sogebanque".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Capital Bank".equalsIgnoreCase(Value)){
            if ("Capital Bank".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Prostitude".equalsIgnoreCase(Value)||"".equalsIgnoreCase(Value)){
            if ("Prostitude".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("BNC".equalsIgnoreCase(Value)){
            if ("BNC".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("BUH".equalsIgnoreCase(Value)){
            if ("BUH".equalsIgnoreCase((checkData))){
                return true;
            }
        }

        if ("BPH".equalsIgnoreCase(Value)){
            if ("BPH".equalsIgnoreCase((checkData))){
                return true;
            }
        }

        if ("0".equalsIgnoreCase(Value)){
            if ("0".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("1".equalsIgnoreCase(Value)){
            if ("1".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("2".equalsIgnoreCase(Value)){
            if ("2".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("2 +".equalsIgnoreCase(Value)||"Plus de 2".equalsIgnoreCase(Value)){
            if ("2 +".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Only One".equalsIgnoreCase(Value)||"Un d’entre eux".equalsIgnoreCase(Value)){
            if ("Only One".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Two of them".equalsIgnoreCase(Value)||"Deux d’entre eux".equalsIgnoreCase(Value)){
            if ("Two of them".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("All of them".equalsIgnoreCase(Value)||"Tous".equalsIgnoreCase(Value)){
            if ("All of them".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Father".equalsIgnoreCase(Value)||"Père".equalsIgnoreCase(Value)){
            if ("Father".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Cousin".equalsIgnoreCase(Value)||"Cousin".equalsIgnoreCase(Value)){
            if ("Cousin".equalsIgnoreCase((checkData))){
                return true;
            }
        }

        if ("Primary".equalsIgnoreCase(Value)||"Primaire".equalsIgnoreCase(Value)){
            if ("Primary".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("Secondary".equalsIgnoreCase(Value)||"Secondaire".equalsIgnoreCase(Value)){
            if ("Secondary".equalsIgnoreCase((checkData))){
                return true;
            }
        }
        if ("University".equalsIgnoreCase(Value)||"Universitaire".equalsIgnoreCase(Value)){
            if ("University".equalsIgnoreCase((checkData))){
                return true;
            }
        }

        return false;
    }

}

