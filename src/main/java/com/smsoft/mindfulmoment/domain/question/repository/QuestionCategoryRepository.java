package com.smsoft.mindfulmoment.domain.question.repository;

import com.smsoft.mindfulmoment.domain.question.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {
}
