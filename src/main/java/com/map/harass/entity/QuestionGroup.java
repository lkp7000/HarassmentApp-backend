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
@Table(name = "question_group")
public class QuestionGroup {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long questionGroupId;

        private String questionGroupName;

        @UpdateTimestamp
        @Column(name = "lastwritten")
        private     LocalDateTime lastwritten;
        @CreationTimestamp
        @Column(name="creationdate",updatable = false)
        private     LocalDate     creationDate;
}
