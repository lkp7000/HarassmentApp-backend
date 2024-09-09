package com.map.harass.service;

import com.map.harass.entity.DomesticViolence;
import com.map.harass.dto.DomesticViolenceDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface ViolenceService {
    DomesticViolenceDTO insertViolenceDetails(DomesticViolence domesticViolence) throws JRException, IOException;

}
