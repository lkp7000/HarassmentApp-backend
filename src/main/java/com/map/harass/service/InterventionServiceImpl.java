package com.map.harass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.harass.entity.Intervention;
import com.map.harass.repository.InterventionRepository;
import com.map.harass.dto.InterventionRequestDTO;
import com.map.harass.dto.InterventionResponseDTO;
import com.map.harass.dto.MessageResponse;
import com.map.harass.mapper.InterventionMapper;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterventionServiceImpl implements InterventionService {

    @Autowired
    private InterventionMapper interventionMapper;
    @Autowired
    private InterventionRepository interventionRepository;
    @Override
    public InterventionResponseDTO saveReportAndIntervention(InterventionRequestDTO request) throws JRException, IOException {

        InterventionResponseDTO interventionResponseDTO = new InterventionResponseDTO();
        MessageResponse response=new MessageResponse();
        Intervention intervention=new Intervention();
        intervention=interventionMapper.interventionRequestDtoTOIntervention(request);
        if(intervention.getLatitude() !=null) {
            String locationName = getLocationName(intervention.getLatitude(), intervention.getLongitude());
            if (locationName != null) {
                intervention.setAddress(locationName);
            }
        }
        intervention= interventionRepository.save(intervention);
        getDataReport(intervention.getInterventionId());
        interventionResponseDTO= interventionMapper.interventionTOInterventionResponseDto(intervention);
        response.setMessage("Report Added");
        return interventionResponseDTO;
    }


    private String getLocationName(String latitude, String longitude) {
        String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode jsonNode = new ObjectMapper().readTree(response);
            return jsonNode.path("display_name").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getDataReport(Long id) throws IOException, JRException {
        Intervention violence = interventionRepository.findById(id).orElse(null);
        if (violence != null) {
            List<Intervention> violenceList = Collections.singletonList(violence);
            String jrxmlPath = "/static/Intervention.jrxml";
            InputStream inputStream = ViolenceServiceImpl.class.getResourceAsStream(jrxmlPath);
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + jrxmlPath);
            }
            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(violenceList);
                Map<String, Object> map = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, dataSource);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
                byte[] pdfContent = byteArrayOutputStream.toByteArray();
                violence.setPdfContent(pdfContent);
                interventionRepository.save(violence);
                return pdfContent;
            } catch (JRException e) {
                e.printStackTrace();
                throw new RuntimeException("Error generating PDF", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return null;
        }
    }

}
