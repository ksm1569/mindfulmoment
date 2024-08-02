package com.smsoft.mindfulmoment.domain.adhdscore.service;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationRequestDto;
import com.smsoft.mindfulmoment.domain.adhdscore.dto.AdhdSurveyCalculationResponseDto;
import com.smsoft.mindfulmoment.domain.adhdscore.entity.AdhdScore;
import com.smsoft.mindfulmoment.domain.adhdscore.repository.AdhdScoreRepository;
import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.common.exception.ErrorCode;
import com.smsoft.mindfulmoment.domain.question.service.UserAnswerService;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import com.smsoft.mindfulmoment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdhdScoreService {
    private final CategoryScoreCalculatorService categoryScoreCalculatorService;
    private final AdhdScoreRepository adhdScoreRepository;
    private final UserRepository userRepository;
    private final UserAnswerService userAnswerService;

    @Transactional(readOnly = true)
    public AdhdSurveyCalculationResponseDto calculateAdhdScore(AdhdSurveyCalculationRequestDto request) {
        Map<String, Integer> categoryScores = categoryScoreCalculatorService.calculateCategoryScores(request.getAnswers());

        AdhdScore adhdScore = AdhdScore.builder()
                .attentionDeficitScore(categoryScores.get("ATTENTION"))
                .hyperactivityScore(categoryScores.get("HYPERACTIVITY"))
                .impulsivityScore(categoryScores.get("IMPULSIVITY"))
                .organizationScore(categoryScores.get("ORGANIZATION"))
                .build();

        return mapToResponseDto(adhdScore);
    }

    @Transactional
    public void saveUserAnswersAndScore(Long userId, AdhdSurveyCalculationRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_EXISTS));

        if (adhdScoreRepository.existsByUser(user)) {
            throw new BusinessException(ErrorCode.SURVEY_ALREADY_SUBMITTED);
        }

        Map<String, Integer> categoryScores = categoryScoreCalculatorService.calculateCategoryScores(request.getAnswers());

        AdhdScore adhdScore = AdhdScore.create(user,
                categoryScores.get("ATTENTION"),
                categoryScores.get("HYPERACTIVITY"),
                categoryScores.get("IMPULSIVITY"),
                categoryScores.get("ORGANIZATION")
        );

        adhdScoreRepository.save(adhdScore);

        userAnswerService.saveUserAnswers(user, request.getAnswers());
    }

    private AdhdSurveyCalculationResponseDto mapToResponseDto(AdhdScore adhdScore) {
        AdhdSurveyCalculationResponseDto responseDto = new AdhdSurveyCalculationResponseDto();
        responseDto.setAttentionDeficitScore(adhdScore.getNormalizedAttentionDeficitScore());
        responseDto.setHyperactivityScore(adhdScore.getNormalizedHyperactivityScore());
        responseDto.setImpulsivityScore(adhdScore.getNormalizedImpulsivityScore());
        responseDto.setOrganizationScore(adhdScore.getNormalizedOrganizationScore());
        responseDto.setTotalScore(adhdScore.getNormalizedTotalScore());
        responseDto.setCalmness(adhdScore.getCalmness());
        responseDto.setConcentration(adhdScore.getConcentration());
        responseDto.setTimeManagement(adhdScore.getTimeManagement());
        responseDto.setDecisionMaking(adhdScore.getDecisionMaking());
        responseDto.setEmotionalControl(adhdScore.getEmotionalControl());
        responseDto.setTaskCompletion(adhdScore.getTaskCompletion());
        return responseDto;
    }
}
