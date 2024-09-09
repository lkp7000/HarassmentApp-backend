package com.map.harass.repository;

import com.map.harass.entity.QuestionOptions;
import com.map.harass.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionsRepository extends JpaRepository<QuestionOptions,Long> {

    List<QuestionOptions> findByQuestions(Questions question);
}
