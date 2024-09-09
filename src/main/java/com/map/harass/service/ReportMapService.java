package com.map.harass.service;

import com.map.harass.dto.ReportMapDTO;
import com.map.harass.dto.TableDataDTO;

public interface ReportMapService {
    ReportMapDTO getALlDataFromDomesticAndIncident();

    TableDataDTO getALlTableData();
}