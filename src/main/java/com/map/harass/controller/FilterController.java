package com.map.harass.controller;

import com.map.harass.service.DomesticDateService;
import com.map.harass.service.FilterService;
import com.map.harass.service.ReportMapService;
import com.map.harass.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/filter")
public class FilterController {

    @Autowired
    private FilterService filterService;
    @Autowired
    private ReportMapService reportMapService;

    @Autowired
    private DomesticDateService domesticDateService;




    @PostMapping("/filterAllByVariousSelections")
    public ResponseEntity<MapDataDTO> getFilterByVariousSelections(@RequestBody MapKey mapKey){
        MapDataDTO mapDataDTO = filterService.getFilterByVariousSelections(mapKey);
        return ResponseEntity.ok(mapDataDTO);
    }

    @GetMapping("/getalldata")
    public ResponseEntity<ReportMapDTO> getALlDataFromDomesticAndIncident(){
        ReportMapDTO mapDataDTO = reportMapService.getALlDataFromDomesticAndIncident();
        return ResponseEntity.ok(mapDataDTO);
    }

    @PostMapping("/filterbydomesticdate")
    public ResponseEntity<ReportMapDTO> filterByDateOfDomestic(@RequestBody ReportMapKeyDTO reportMapKeyDTO) {
        ReportMapDTO reportMapDTO = domesticDateService.filterByDateOfDomestic(reportMapKeyDTO);
        return ResponseEntity.ok(reportMapDTO);
    }

    @GetMapping("/getalltabledata")
    public ResponseEntity<TableDataDTO> getALlTableData(){
        TableDataDTO tableDataDTO = reportMapService.getALlTableData();
        return ResponseEntity.ok(tableDataDTO);
    }

    @PostMapping("/filtertablebydate")
    public ResponseEntity<ReportTableMapDTO> filterTableByDate(@RequestBody ReportTableMapKeyDTO reportTableMapKeyDTO) {
        ReportTableMapDTO reportMapKeyDTO = domesticDateService.filterTableByDate(reportTableMapKeyDTO);
        return ResponseEntity.ok(reportMapKeyDTO);
    }


}
