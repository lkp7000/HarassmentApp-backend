package com.map.harass.service;

import com.map.harass.entity.DomesticViolence;
import com.map.harass.entity.Intervention;
import com.map.harass.repository.DomesticViolenceRepo;
import com.map.harass.repository.InterventionRepository;
import com.map.harass.dto.ReportDomesticDTO;
import com.map.harass.dto.ReportInterventionDTO;
import com.map.harass.dto.ReportMapDTO;
import com.map.harass.dto.TableDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportMapServiceImpl implements ReportMapService {

    @Autowired
    private DomesticViolenceRepo domesticViolenceRepo;
    @Autowired
    private InterventionRepository interventionRepository;
    @Override
    public ReportMapDTO getALlDataFromDomesticAndIncident() {
        ReportMapDTO reportMapDTO=new ReportMapDTO();

        List<ReportInterventionDTO> interventionDTOList = new ArrayList<>();
        List<Intervention> interventionList=interventionRepository.findAll();

        for(Intervention intervention: interventionList){
            ReportInterventionDTO interventionDTO = new ReportInterventionDTO();
            if (intervention.getLatitude() != null) {
                interventionDTO.setLatitude(intervention.getLatitude());
            }

            if (intervention.getLongitude() != null) {
                interventionDTO.setLongitude(intervention.getLongitude());
            }
            interventionDTO.setDate(intervention.getIntervention_date());
            interventionDTO.setLocation(intervention.getIntervention_address());
            interventionDTO.setDescription(intervention.getDescription());
            interventionDTO.setTime(intervention.getIncident_time());
            interventionDTO.setType("Intervention");
            interventionDTOList.add(interventionDTO);
            reportMapDTO.setReportInterventionDTOS(interventionDTOList);
        }

        List<ReportDomesticDTO> domesticDTOList = new ArrayList<>();
        List<DomesticViolence> domesticViolencesList= domesticViolenceRepo.findAll();


        for(DomesticViolence domesticViolence: domesticViolencesList){
            ReportDomesticDTO domesticDTO = new ReportDomesticDTO();
            if (domesticViolence.getLatitude() != null) {
                domesticDTO.setLatitude(domesticViolence.getLatitude());
            }

            if (domesticViolence.getLongitude() != null) {
                domesticDTO.setLongitude(domesticViolence.getLongitude());
            }
            domesticDTO.setDate(domesticViolence.getIncident_date());
            domesticDTO.setLocation(domesticViolence.getIncident_address());
            domesticDTO.setDescription(domesticViolence.getDescription());
            domesticDTO.setTime(domesticViolence.getIncident_time());
            domesticDTO.setType("Harassment");
            domesticDTOList.add(domesticDTO);
            reportMapDTO.setAllDomesticDTOList(domesticDTOList);
        }

        return reportMapDTO;
    }

    @Override
    public TableDataDTO getALlTableData() {
        TableDataDTO tableDataDTO=new TableDataDTO();
        List<ReportDomesticDTO> domesticDTOList = new ArrayList<>();
        ReportDomesticDTO reportDomesticDTO1 = new ReportDomesticDTO();
        List<Intervention> interventionList=interventionRepository.findAll();
        List<DomesticViolence> domesticViolencesList= domesticViolenceRepo.findAll();
        ReportDomesticDTO reportDomesticDTO=new ReportDomesticDTO();

        for(Intervention intervention: interventionList){

            if (intervention.getLatitude() != null) {
                reportDomesticDTO.setLatitude(intervention.getLatitude());
            }
            if (intervention.getLongitude() != null) {
                reportDomesticDTO.setLongitude(intervention.getLongitude());
            }
            reportDomesticDTO.setDate(intervention.getIntervention_date());
            reportDomesticDTO.setLocation(intervention.getIntervention_address());
            reportDomesticDTO.setDescription(intervention.getDescription());
            reportDomesticDTO.setTime(intervention.getIncident_time());
            reportDomesticDTO.setType("Intervention");
            domesticDTOList.add(reportDomesticDTO);
        }

        for(DomesticViolence domesticViolence: domesticViolencesList){
            if (domesticViolence.getLatitude() != null) {
                reportDomesticDTO1.setLatitude(domesticViolence.getLatitude());
            }
            if (domesticViolence.getLongitude() != null) {
                reportDomesticDTO1.setLongitude(domesticViolence.getLongitude());
            }
            reportDomesticDTO1.setDate(domesticViolence.getIncident_date());
            reportDomesticDTO1.setLocation(domesticViolence.getIncident_address());
            reportDomesticDTO1.setDescription(domesticViolence.getDescription());
            reportDomesticDTO1.setTime(domesticViolence.getIncident_time());
            reportDomesticDTO1.setType("Harassment");
            domesticDTOList.add(reportDomesticDTO1);
        }
        tableDataDTO.setAllTableData(domesticDTOList);
        return tableDataDTO;
    }
}
