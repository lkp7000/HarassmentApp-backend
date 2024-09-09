package com.map.harass.repository;

import com.map.harass.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findByCityName(String questionAnswerText);

    Address findByLatitude(String latitude);
}
