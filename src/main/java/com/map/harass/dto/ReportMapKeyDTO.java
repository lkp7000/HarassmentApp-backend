package com.map.harass.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportMapKeyDTO {

    private Map<String, LocalDate> requestMap = new HashMap<>();

}
