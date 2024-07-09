package com.smsoft.mindfulmoment.domain.question.service;

import com.smsoft.mindfulmoment.domain.question.dto.QuestionDto;
import com.smsoft.mindfulmoment.domain.question.repository.QuestionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepositoryCustom questionRepositoryCustom;
    @Transactional(readOnly = true)
    public List<QuestionDto> getRandomQuestionsForAllCategories() {
        return questionRepositoryCustom.findRandomQuestionsForAllCategories();
    }

    @Transactional(readOnly = true)
    public int calculateAdhdScore(Map<Long, Integer> answers) {
        double averageScore = answers.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return (int) (averageScore * 25);
    }
}
