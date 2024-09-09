package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {

    private Long id;
    private byte[] image;
    private String title;
    private String description;
}

