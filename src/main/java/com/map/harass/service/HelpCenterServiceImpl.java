package com.map.harass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.map.harass.entity.HelpCenter;
import com.map.harass.repository.HelpCenterRepository;
import com.map.harass.dto.HelpCenterDTO;
import com.map.harass.dto.HelpCenterFilterResposeDTO;
import com.map.harass.dto.HelpCenterMapKeyDTO;
import com.map.harass.mapper.HelpCenterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HelpCenterServiceImpl implements HelpCenterService{
    @Autowired
    HelpCenterRepository helpCenterRepository;
    @Autowired
    HelpCenterMapper helpCenterMapper;
    @Override
    public HelpCenterDTO saveOrganisation(HelpCenterDTO helpCenterDTO) {
        HelpCenter helpCenter=new HelpCenter();
        helpCenter=helpCenterMapper.helpCenterDtoTOHelpCenter(helpCenterDTO);

        if(helpCenter.getLatitude() !=null) {
            String locationName = getLocationName(helpCenter.getLatitude(), helpCenter.getLongitude());
            if (locationName != null) {
                helpCenter.setAddress(locationName);
            }
        }
        helpCenter= helpCenterRepository.save(helpCenter);
        helpCenterDTO=helpCenterMapper.helpCenterTOHelpCenterDto(helpCenter);
        return helpCenterDTO;
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

    @Override
    public List<HelpCenterFilterResposeDTO> filterByOrganisationType(HelpCenterMapKeyDTO organisationType) {
        List<HelpCenterFilterResposeDTO> helpCenterFilterResposeDTO=new ArrayList<>();

        List<HelpCenter> helpCenterList=helpCenterRepository.findAll();
        List<HelpCenterDTO> helpCenterDTOList=helpCenterMapper.helpCenterListTOHelpCenterDtoList(helpCenterList);
        if ("All".equalsIgnoreCase(organisationType.getOrganisationType())){
            helpCenterFilterResposeDTO=helpCenterMapper.helpCenterDtoListToFilterResponseDtoList(helpCenterDTOList);
        }
        else {
            helpCenterFilterResposeDTO = helpCenterDTOList.stream()
                    .filter(dto -> organisationType.getOrganisationType().equalsIgnoreCase(dto.getOrganisationType()))
                    .map(helpCenterMapper::helpCenterDtoToFilterResponseDto)
                    .collect(Collectors.toList());
        }
        return helpCenterFilterResposeDTO;
    }
}
