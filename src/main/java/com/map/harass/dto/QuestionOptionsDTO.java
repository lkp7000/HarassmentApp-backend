package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionOptionsDTO {


    private Long questionOptionID;
    private String optionText;
    private Long questionID;

}
