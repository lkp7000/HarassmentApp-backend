package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterDTO {
    private Long          helpCenterId;
    private     String        organisationName;
    private     String        email;
    private     String        phoneNumber;
    private     String        organisationType;
    private     String        address;
    private     String        longitude;
    private     String        latitude;
    private     LocalDateTime lastWritten;
    private LocalDate creationDate;
}
