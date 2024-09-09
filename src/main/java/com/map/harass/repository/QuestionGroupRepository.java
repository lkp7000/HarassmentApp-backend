package com.map.harass.repository;

import com.map.harass.entity.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup,Long> {

}
