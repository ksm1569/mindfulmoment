package com.smsoft.mindfulmoment.presentation.api.adhdscore;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationRequestDto;
import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationResponseDto;
import com.smsoft.mindfulmoment.domain.adhdscore.service.AdhdScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adhd-survey")
@RequiredArgsConstructor
public class AdhdSurveyController {

    private final AdhdScoreService adhdScoreService;

    @PostMapping("/calculate")
    public ResponseEntity<AdhdSurveyCalculationResponseDto> calculateAdhdScore(
            @RequestBody AdhdSurveyCalculationRequestDto request
    ) {
        AdhdSurveyCalculationResponseDto response = adhdScoreService.calculateAdhdScore(request);
        return ResponseEntity.ok(response);
    }
}