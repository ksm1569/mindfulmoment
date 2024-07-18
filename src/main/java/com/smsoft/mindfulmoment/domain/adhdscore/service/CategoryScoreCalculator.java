package com.smsoft.mindfulmoment.domain.adhdscore.service;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.SurveyAnswerDto;
import com.smsoft.mindfulmoment.domain.question.entity.QuestionCategory;
import com.smsoft.mindfulmoment.domain.question.repository.QuestionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryScoreCalculator {
    private final QuestionCategoryRepository questionCategoryRepository;

    public Map<String, Integer> calculateCategoryScores(List<SurveyAnswerDto> answers) {
        List<QuestionCategory> categories = questionCategoryRepository.findAll();
        return categories.stream()
                .collect(Collectors.toMap(
                        QuestionCategory::getCode,
                        category -> category.calculateCategoryScore(answers)
                ));
    }
}
