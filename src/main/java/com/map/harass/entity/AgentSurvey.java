package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agent_servey")
public class AgentSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentSurveyID;

    @ManyToOne
    @JoinColumn(name = "agentID")
    private Agent agentID;

    @ManyToOne
    @JoinColumn(name = "surveyID")
    private Survey surveyID;

    private String latitude;
    private String longitude;
    private String fullAddress;
    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;
    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDateTime  creationDate;

}
