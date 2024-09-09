package com.map.harass.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "helpcenters")
public class HelpCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long helpCenterId;
    private String organisation_Name;
    private String email;
    private String phone_Number;
    private String organisation_Type;
    private String address;
    private String longitude;
    private String latitude;
    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;

    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate     creationDate;

}
