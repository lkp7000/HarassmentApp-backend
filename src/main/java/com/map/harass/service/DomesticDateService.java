package com.map.harass.service;

import com.map.harass.dto.ReportMapDTO;
import com.map.harass.dto.ReportMapKeyDTO;
import com.map.harass.dto.ReportTableMapDTO;
import com.map.harass.dto.ReportTableMapKeyDTO;

public interface DomesticDateService {
    ReportMapDTO filterByDateOfDomestic(ReportMapKeyDTO reportMapKeyDTO);

    ReportTableMapDTO filterTableByDate(ReportTableMapKeyDTO reportTableMapKeyDTO);
}
