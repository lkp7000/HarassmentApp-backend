package com.map.harass.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    private long id;
    private  String cityName;
    private String latitude;
    private String longitude;

}
