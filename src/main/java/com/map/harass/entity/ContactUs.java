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
@Table(name = "contactus")
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String fullName;
    private String contactEmail;
    private String contactPhone;
    private String description;

    @UpdateTimestamp
    @Column(name = "lastwritten")
    private LocalDateTime lastwritten;

    @CreationTimestamp
    @Column(name="creationdate",updatable = false)
    private LocalDate creationDate;

}
