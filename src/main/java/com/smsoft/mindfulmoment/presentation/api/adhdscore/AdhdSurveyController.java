package com.smsoft.mindfulmoment.presentation.api.adhdscore;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationRequestDto;
import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationResponseDto;
import com.smsoft.mindfulmoment.domain.adhdscore.service.AdhdScoreService;
import com.smsoft.mindfulmoment.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adhd-survey")
@RequiredArgsConstructor
public class AdhdSurveyController {

    private final AdhdScoreService adhdScoreService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/calculate")
    public ResponseEntity<AdhdSurveyCalculationResponseDto> calculateAdhdScore(
            @RequestBody AdhdSurveyCalculationRequestDto request
    ) {
        AdhdSurveyCalculationResponseDto response = adhdScoreService.calculateAdhdScore(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveSurveyResults(
        @RequestBody AdhdSurveyCalculationRequestDto request,
        @CookieValue("access_token") String token
    ) {
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        adhdScoreService.saveUserAnswersAndScore(userId, request);
        return ResponseEntity.ok().build();
    }
}