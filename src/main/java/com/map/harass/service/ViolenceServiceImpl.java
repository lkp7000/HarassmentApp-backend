package com.map.harass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.harass.entity.DomesticViolence;
import com.map.harass.repository.AddressRepository;
import com.map.harass.repository.DomesticViolenceRepo;
import com.map.harass.dto.DomesticViolenceDTO;
import com.map.harass.dto.MessageResponse;
import com.map.harass.mapper.DomesticViolenceMapper;
import jakarta.servlet.http.HttpServletRequest;
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
public class ViolenceServiceImpl implements ViolenceService{
    @Autowired
    private DomesticViolenceRepo domesticViolenceRepo;
    @Autowired
    private DomesticViolenceMapper domesticViolenceMapper;
    @Autowired
    private       AddressRepository  addressRepository;
    @Autowired
    private  HttpServletRequest request;

    public ViolenceServiceImpl() {
    }


    @Override
    public DomesticViolenceDTO insertViolenceDetails(DomesticViolence domesticViolence) throws JRException, IOException {
        DomesticViolenceDTO domesticViolenceDTO=new DomesticViolenceDTO();
        MessageResponse response=new MessageResponse();
        if(domesticViolence.getLatitude() !=null) {
            String locationName = getLocationName(domesticViolence.getLatitude(), domesticViolence.getLongitude());
            if (locationName != null) {
                domesticViolence.setAddress(locationName);
            }
        }
        domesticViolence=domesticViolenceRepo.save(domesticViolence);
        getDataReport(domesticViolence.getDomestic_violenceId());
        domesticViolenceDTO=domesticViolenceMapper.domesticViolenceToDomesticViolenceDto(domesticViolence);
        response.setMessage("Report Added");
        return domesticViolenceDTO;
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
        DomesticViolence violence = domesticViolenceRepo.findById(id).orElse(null);
        if (violence != null) {
            List<DomesticViolence> violenceList = Collections.singletonList(violence);
            String jrxmlPath = "/static/violence.jrxml";
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
                domesticViolenceRepo.save(violence);
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
