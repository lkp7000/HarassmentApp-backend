package com.map.harass.repository;

import com.map.harass.entity.HelpCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpCenterRepository extends JpaRepository<HelpCenter,Long> {
}
