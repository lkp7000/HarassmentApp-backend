package com.map.harass.service;

import com.map.harass.entity.DomesticViolence;
import com.map.harass.entity.Intervention;
import com.map.harass.repository.DomesticViolenceRepo;
import com.map.harass.repository.InterventionRepository;
import com.map.harass.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DomesticDateServiceImpl implements DomesticDateService {

    @Autowired
    private DomesticViolenceRepo domesticViolenceRepo;
    @Autowired
    InterventionRepository interventionRepository;

    public ReportMapDTO filterByDateOfDomestic(ReportMapKeyDTO reportMapKeyDTO) {
        ReportMapDTO reportMapDTO=new ReportMapDTO();

        List<ReportDomesticDTO> reportDomesticDTO=new ArrayList<>();


        List<ReportInterventionDTO> reportInterventionDTOList=new ArrayList<>();
        List<DomesticViolence> domesticViolenceList = domesticViolenceRepo.findAll();
        List<Intervention> interventionList = interventionRepository.findAll();
        LocalDate startDate =reportMapKeyDTO.getRequestMap().get("startDate");
        LocalDate endDate =reportMapKeyDTO.getRequestMap().get("endDate");
        List<DomesticViolence> filteredList = domesticViolenceList.stream()
                    .filter(dv ->dv.getIncident_date() != null && dv.getIncident_date().isAfter(startDate) && dv.getIncident_date().isBefore(endDate))
                    .collect(Collectors.toList());
        // For domestic
        for (DomesticViolence domesticViolence: filteredList){
            ReportDomesticDTO reportDomesticDTO1=new  ReportDomesticDTO();
            reportDomesticDTO1.setLatitude(domesticViolence.getLatitude());
            reportDomesticDTO1.setLongitude(domesticViolence.getLongitude());
            reportDomesticDTO1.setType("Harassment");
            reportDomesticDTO1.setDate(domesticViolence.getIncident_date());
            reportDomesticDTO1.setLocation(domesticViolence.getAddress());
            reportDomesticDTO1.setDescription(domesticViolence.getDescription());

            reportDomesticDTO.add(reportDomesticDTO1);

        }
        reportMapDTO.setAllDomesticDTOList(reportDomesticDTO);

        // For intervention
        List<Intervention> filteredList1 = interventionList.stream()
                .filter(dv -> dv.getIntervention_date() != null && dv.getIntervention_date().isAfter(startDate) && dv.getIntervention_date().isBefore(endDate))
                .collect(Collectors.toList());
        for (Intervention intervention: filteredList1){
            ReportInterventionDTO reportInterventionDTO =new ReportInterventionDTO();
            reportInterventionDTO.setLatitude(intervention.getLatitude());
            reportInterventionDTO.setLongitude(intervention.getLongitude());
            reportInterventionDTO.setType("Intervention");
            reportInterventionDTO.setDate(intervention.getIntervention_date());
            reportInterventionDTO.setLocation(intervention.getAddress());
            reportInterventionDTO.setDescription(intervention.getDescription());
            reportInterventionDTOList.add(reportInterventionDTO);
        }
        reportMapDTO.setAllDomesticDTOList(reportDomesticDTO);
        reportMapDTO.setReportInterventionDTOS(reportInterventionDTOList);
        return  reportMapDTO;
    }

    @Override
    public ReportTableMapDTO filterTableByDate(ReportTableMapKeyDTO reportTableMapKeyDTO) {

        ReportTableMapDTO reportTableMapDTO= new ReportTableMapDTO();
        List<ReportDomesticDTO> reportDomesticDTOList=new ArrayList<>();
        List<ReportInterventionDTO> reportInterventionDTOList= new ArrayList<>();


        List<DomesticViolence> domesticViolenceList = domesticViolenceRepo.findAll();
        List<Intervention> interventionList = interventionRepository.findAll();
        LocalDate startDate =reportTableMapKeyDTO.getRequestTableMap().get("startDate");
        LocalDate endDate =reportTableMapKeyDTO.getRequestTableMap().get("endDate");

        if(("All").equalsIgnoreCase(reportTableMapKeyDTO.getType())){
            List<DomesticViolence> filteredList = domesticViolenceList.stream()
                    .filter(dv ->dv.getIncident_date() != null && dv.getIncident_date().isAfter(startDate) && dv.getIncident_date().isBefore(endDate))
                    .collect(Collectors.toList());
            // For domestic
            for (DomesticViolence domesticViolence: filteredList){
                ReportDomesticDTO reportDomesticDTO1=new  ReportDomesticDTO();
                reportDomesticDTO1.setLatitude(domesticViolence.getLatitude());
                reportDomesticDTO1.setLongitude(domesticViolence.getLongitude());
                reportDomesticDTO1.setTime(domesticViolence.getIncident_time());
                reportDomesticDTO1.setType("Harassment");
                reportDomesticDTO1.setDate(domesticViolence.getIncident_date());
                reportDomesticDTO1.setLocation(domesticViolence.getAddress());
                reportDomesticDTO1.setDescription(domesticViolence.getDescription());
                reportDomesticDTOList.add(reportDomesticDTO1);
            }
            reportTableMapDTO.setAllTableDTOList(reportDomesticDTOList);

            // For intervention
            List<Intervention> filteredList1 = interventionList.stream()
                    .filter(dv -> dv.getIntervention_date() != null && dv.getIntervention_date().isAfter(startDate) && dv.getIntervention_date().isBefore(endDate))
                    .collect(Collectors.toList());
            for (Intervention intervention: filteredList1){
                ReportDomesticDTO reportInterventionDTO=new  ReportDomesticDTO();
                reportInterventionDTO.setLatitude(intervention.getLatitude());
                reportInterventionDTO.setLongitude(intervention.getLongitude());
                reportInterventionDTO.setType("Intervention");
                reportInterventionDTO.setDate(intervention.getIntervention_date());
                reportInterventionDTO.setTime(intervention.getIncident_time());
                reportInterventionDTO.setLocation(intervention.getAddress());
                reportInterventionDTO.setDescription(intervention.getDescription());
                reportDomesticDTOList.add(reportInterventionDTO);

            }
            reportTableMapDTO.setAllTableDTOList(reportDomesticDTOList);

        }
        else if(("Harassment").equalsIgnoreCase(reportTableMapKeyDTO.getType())){
            List<DomesticViolence> filteredList = domesticViolenceList.stream()
                    .filter(dv ->dv.getIncident_date() != null && dv.getIncident_date().isAfter(startDate) && dv.getIncident_date().isBefore(endDate))
                    .collect(Collectors.toList());
            // For domestic
            for (DomesticViolence domesticViolence: filteredList){
                ReportDomesticDTO reportDomesticDTO1=new  ReportDomesticDTO();
                reportDomesticDTO1.setLatitude(domesticViolence.getLatitude());
                reportDomesticDTO1.setLongitude(domesticViolence.getLongitude());
                reportDomesticDTO1.setTime(domesticViolence.getIncident_time());
                reportDomesticDTO1.setType("Harassment");
                reportDomesticDTO1.setDate(domesticViolence.getIncident_date());
                reportDomesticDTO1.setLocation(domesticViolence.getAddress());
                reportDomesticDTO1.setDescription(domesticViolence.getDescription());
                reportDomesticDTOList.add(reportDomesticDTO1);
            }
            reportTableMapDTO.setAllTableDTOList(reportDomesticDTOList);

        }

       else if(("Intervention").equalsIgnoreCase(reportTableMapKeyDTO.getType())){
            // For intervention
            List<Intervention> filteredList1 = interventionList.stream()
                    .filter(dv -> dv.getIntervention_date() != null && dv.getIntervention_date().isAfter(startDate) && dv.getIntervention_date().isBefore(endDate))
                    .collect(Collectors.toList());
            for (Intervention intervention: filteredList1){
                ReportDomesticDTO reportInterventionDTO=new  ReportDomesticDTO();
                reportInterventionDTO.setLatitude(intervention.getLatitude());
                reportInterventionDTO.setLongitude(intervention.getLongitude());
                reportInterventionDTO.setTime(intervention.getIncident_time());
                reportInterventionDTO.setType("Intervention");
                reportInterventionDTO.setDate(intervention.getIntervention_date());
                reportInterventionDTO.setLocation(intervention.getAddress());
                reportInterventionDTO.setDescription(intervention.getDescription());
                reportDomesticDTOList.add(reportInterventionDTO);

            }
            reportTableMapDTO.setAllTableDTOList(reportDomesticDTOList);
        }

        return reportTableMapDTO;
    }
}
