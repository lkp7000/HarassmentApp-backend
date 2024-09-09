package com.map.harass.repository;


import com.map.harass.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Long> {
    Questions findByShortText(String shortText);
}
