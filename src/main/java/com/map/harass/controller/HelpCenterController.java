package com.map.harass.controller;

import com.map.harass.service.HelpCenterService;
import com.map.harass.dto.HelpCenterDTO;
import com.map.harass.dto.HelpCenterFilterResposeDTO;
import com.map.harass.dto.HelpCenterMapKeyDTO;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/helpcenter")
@CrossOrigin("*")
public class HelpCenterController {

    @Autowired
    HelpCenterService helpCenterService;

    @PostMapping(value = "/addorganisation", produces = "application/json")
    public ResponseEntity<HelpCenterDTO> createHelpCenter(@RequestBody HelpCenterDTO helpCenterDTO) throws JRException, IOException {
        HelpCenterDTO helpCenterDTO1 = helpCenterService.saveOrganisation(helpCenterDTO);
        return new ResponseEntity<>(helpCenterDTO1, HttpStatus.OK);
    }
    @PostMapping(value = "/filterhelpcenterbytype", produces = "application/json")
    public ResponseEntity<List<HelpCenterFilterResposeDTO>> filterHelpCenterByType(@RequestBody HelpCenterMapKeyDTO organisationType) throws JRException, IOException {
        List<HelpCenterFilterResposeDTO> helpCenterFilterResposeDTOList = helpCenterService.filterByOrganisationType(organisationType);
        return new ResponseEntity<>(helpCenterFilterResposeDTOList, HttpStatus.OK);
    }

}
