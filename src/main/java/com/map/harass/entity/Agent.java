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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agent")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentID;
    private String agentName;

    @Column(unique = true)
    private String email;
    private  String password;
    private Boolean isVerified;
    private String role;
    private  String status;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;
    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;


}
