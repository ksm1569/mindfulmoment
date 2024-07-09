package com.smsoft.mindfulmoment.presentation.api;

import com.smsoft.mindfulmoment.domain.question.dto.QuestionDto;
import com.smsoft.mindfulmoment.domain.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/questions")
@RestController
public class QuestionApiController {
    private final QuestionService questionService;

    @GetMapping("/random")
    public ResponseEntity<List<QuestionDto>> getRandomQuestions() {
        List<QuestionDto> questions = questionService.getRandomQuestionsForAllCategories();
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/submit")
    public ResponseEntity<Integer> submitAnswers(@RequestBody Map<Long, Integer> answers) {
        int adhdScore = questionService.calculateAdhdScore(answers);
        return ResponseEntity.ok(adhdScore);
    }
}
