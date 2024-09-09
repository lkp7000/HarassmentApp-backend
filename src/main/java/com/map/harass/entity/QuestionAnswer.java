package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_answer")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionAnswerID;
    private Long selectedQuestionOptionID;
    private String questionAnswerText;

    @ManyToOne
    @JoinColumn(name = "questionID")
    private Questions questionID;

    @ManyToOne
    @JoinColumn(name = "agentSurveyID")
    private AgentSurvey   agentSurveyID;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;

    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate creationDate;



}