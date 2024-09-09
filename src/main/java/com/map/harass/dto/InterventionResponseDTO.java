package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterventionResponseDTO {

    private Long interventionId;
    private byte[]    pdfContent;
    private     String    full_Name;
    private     String    email;
    private     String    phone_number;
    private String description;
    private     String    interventionName;
    private     LocalDate intervention_date;
    private     String    Intervention_address;
    private String longitude;
    private String latitude;

}
