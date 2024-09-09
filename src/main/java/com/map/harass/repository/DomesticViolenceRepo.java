package com.map.harass.repository;

import com.map.harass.entity.DomesticViolence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomesticViolenceRepo extends JpaRepository<DomesticViolence,Long> {
}
