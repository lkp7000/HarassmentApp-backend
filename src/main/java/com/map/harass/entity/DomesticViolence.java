package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "domestic_violence")
public class DomesticViolence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domestic_violenceId;

    @Lob
    @Column(name = "pdf_content", columnDefinition="LONGBLOB")
    private byte[] pdfContent;
    private String full_Name;
    private String email;
    private String phone_number;
    private String city;
    private String address;
    private String description;
    private String    harrasserName;
    private Time incident_time;
    private LocalDate incident_date;
    private String incident_address;
    private String longitude;
    private String latitude;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;

    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;

}
