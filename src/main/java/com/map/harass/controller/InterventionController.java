package com.map.harass.controller;

import com.map.harass.entity.Intervention;
import com.map.harass.repository.InterventionRepository;
import com.map.harass.service.InterventionService;
import com.map.harass.dto.InterventionRequestDTO;
import com.map.harass.dto.InterventionResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/intervention")
@CrossOrigin("*")
    public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    @Autowired
    private InterventionRepository interventionRepository;

    @PostMapping(value = "/intervention", produces = "application/json")
    public ResponseEntity<InterventionResponseDTO> createAllIntervention(@RequestBody InterventionRequestDTO request) throws JRException, IOException {
        InterventionResponseDTO qRes = interventionService.saveReportAndIntervention(request);
        return new ResponseEntity<>(qRes, HttpStatus.OK);
    }

    @GetMapping("/{pdfId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long pdfId) {
        Intervention pdfDocument = interventionRepository.findById(pdfId)
                .orElseThrow(() -> new EntityNotFoundException("PDF not found"));
        byte[] pdfContent = pdfDocument.getPdfContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "pdf_" + pdfId + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfContent.length);
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }
}
