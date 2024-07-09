package com.smsoft.mindfulmoment.domain.question.repository;

import com.smsoft.mindfulmoment.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
