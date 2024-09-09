package com.map.harass.controller;

import com.map.harass.entity.DomesticViolence;
import com.map.harass.repository.DomesticViolenceRepo;
import com.map.harass.service.ViolenceService;
import com.map.harass.dto.DomesticViolenceDTO;
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
@CrossOrigin("*")
@RequestMapping("/v1/api/domestic")
public class ViolenceController {
    @Autowired
    private ViolenceService  violenceService;
    @Autowired
    private DomesticViolenceRepo domesticViolenceRepo;
    @PostMapping("/createViolence")
    public DomesticViolenceDTO createDomesticViolence(@RequestBody DomesticViolence domesticViolence) throws JRException, IOException {
        DomesticViolenceDTO domesticViolence1;
        domesticViolence1=violenceService.insertViolenceDetails(domesticViolence);
        return domesticViolence1;
    }

    @GetMapping("/{pdfId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long pdfId) {
        DomesticViolence pdfDocument = domesticViolenceRepo.findById(pdfId)
                .orElseThrow(() -> new EntityNotFoundException("PDF not found"));
        byte[] pdfContent = pdfDocument.getPdfContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "pdf_" + pdfId + ".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentLength(pdfContent.length);
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }


}
