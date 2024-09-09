package com.map.harass.repository;

import com.map.harass.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    public Agent findByemail(String email);
}
