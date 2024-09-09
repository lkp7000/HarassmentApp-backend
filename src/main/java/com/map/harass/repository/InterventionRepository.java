package com.map.harass.repository;

import com.map.harass.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention,Long> {

}
