package com.map.harass.repository;


import com.map.harass.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs ,Long> {
}
