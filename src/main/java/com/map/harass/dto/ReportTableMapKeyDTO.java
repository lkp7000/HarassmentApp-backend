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
public class ReportTableMapKeyDTO {

    private Map<String, LocalDate> requestTableMap = new HashMap<>();
    private  String type;

}
