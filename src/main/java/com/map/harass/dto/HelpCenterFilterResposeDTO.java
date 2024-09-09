package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HelpCenterFilterResposeDTO {

    private String latitude;
    private String longitude;
    private String address;
    private String organisationType;
}
