package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswersDTO {

    private Long answerId;
    private QuestionsDTO questionsDTO;
    private String       questionAnswerText;
    private LocalDateTime lastwritten;
    private LocalDate     creationDate;

}
