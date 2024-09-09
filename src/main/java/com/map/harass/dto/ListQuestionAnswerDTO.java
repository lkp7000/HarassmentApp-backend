package com.map.harass.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListQuestionAnswerDTO {

    private  Long questionID;
    private  QuestionAnswerDTO questionAnswerDTOList;

}
