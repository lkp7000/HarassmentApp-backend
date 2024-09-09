package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseQuestionAnswerDTO {

    private Long questionId;
    private Long questionAnswerId;
    private String questionText;
    private String questionAnswerText;
    private String questionGroup;

}
