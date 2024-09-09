package com.map.harass.service;

import com.map.harass.dto.HelpCenterDTO;
import com.map.harass.dto.HelpCenterFilterResposeDTO;
import com.map.harass.dto.HelpCenterMapKeyDTO;

import java.util.List;


public interface HelpCenterService {
    HelpCenterDTO saveOrganisation(HelpCenterDTO helpCenterDTO);

    List<HelpCenterFilterResposeDTO> filterByOrganisationType(HelpCenterMapKeyDTO organisationType);
}
