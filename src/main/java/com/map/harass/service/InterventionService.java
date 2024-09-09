package com.map.harass.service;

import com.map.harass.dto.InterventionRequestDTO;
import com.map.harass.dto.InterventionResponseDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

public interface InterventionService {
    InterventionResponseDTO saveReportAndIntervention(InterventionRequestDTO request) throws JRException, IOException;
}
