package com.map.harass.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapKey {

    private Map<String, String> requestMap =new HashMap<>();


}
