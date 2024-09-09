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
@Table(name = "answers")
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions questions;
    private String questionAnswerText;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;
    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;

}
