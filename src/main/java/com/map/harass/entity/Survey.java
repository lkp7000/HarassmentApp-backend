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
@Table(name = "survey")
public class Survey {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long surveyId;
        private String surveyDescription;

        @UpdateTimestamp
        @Column(name = "lastwritten")
        private     LocalDateTime lastwritten;
        @CreationTimestamp
        @Column(name="creationdate",updatable = false)
        private     LocalDate     creationDate;

        @ManyToOne
        @JoinColumn(name = "agent_id")
        private Agent agent;
}
