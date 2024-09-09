package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionID;
    private String questionText;
    private String status;
    private  Long surveyID;


    @ManyToOne
    @JoinColumn(name = "question_group_id")
    private QuestionGroup questionGroupID;

    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOptions> questionOptionsID;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;

    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;

    private String shortText;
    private String type;

}
