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
public class DomesticViolenceDTO {
    private Long domestic_violenceId;
    private byte[] pdfContent;
    private String full_Name;
    private String email;
    private String phone_number;
    private String address;
    private String description;
    private String harrasserName;
    private LocalDate incident_date;
    private String incident_address;
}
