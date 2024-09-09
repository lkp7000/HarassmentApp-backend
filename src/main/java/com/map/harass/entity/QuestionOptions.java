package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_options")
public class QuestionOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionOptionID;

    private String optionText;

    @ManyToOne
    @JoinColumn(name = "questionID")
    private Questions questions;

    private String latitude;
    private String longitude;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;
    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;

}
