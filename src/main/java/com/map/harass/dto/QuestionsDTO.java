package com.map.harass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionsDTO {

    private Long             questionID;
    private String           questionText;
    private long surveyID;
    private String           status;
    private QuestionGroupDTO questionGroupID;
    private List<QuestionOptionsDTO>  questionOptionsID;
    private     String           questionCode;
    private LocalDateTime lastWritten;
    private String shortKey;
    private LocalDate     creationDate;
    private String type;

}
