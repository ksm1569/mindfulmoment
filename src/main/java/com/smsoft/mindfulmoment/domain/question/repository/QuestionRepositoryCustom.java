package com.smsoft.mindfulmoment.domain.question.repository;

import com.smsoft.mindfulmoment.domain.question.dto.QuestionDto;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<QuestionDto> findRandomQuestionsForAllCategories();
}
