package com.smsoft.mindfulmoment.domain.adhdscore.service;

import com.smsoft.mindfulmoment.domain.adhdscore.entity.AdhdScore;
import com.smsoft.mindfulmoment.domain.adhdscore.repository.AdhdScoreRepository;
import com.smsoft.mindfulmoment.domain.question.entity.UserAnswer;
import com.smsoft.mindfulmoment.domain.question.repository.UserAnswerRepository;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdhdScoreService {
    private final AdhdScoreRepository adhdScoreRepository;
    private final UserAnswerRepository userAnswerRepository;

    @Transactional
    public AdhdScore calculateAndSaveScore(User user) {
        List<UserAnswer> userAnswers = userAnswerRepository.findByUser(user);

        Integer attentionDeficitScore = calculateCategoryScore(userAnswers, "ATTENTION");
        Integer hyperactivityScore = calculateCategoryScore(userAnswers, "HYPERACTIVITY");
        Integer impulsivityScore = calculateCategoryScore(userAnswers, "IMPULSIVITY");
        Integer organizationScore = calculateCategoryScore(userAnswers, "ORGANIZATION");

        AdhdScore score = AdhdScore.builder()
                .user(user)
                .attentionDeficitScore(attentionDeficitScore)
                .hyperactivityScore(hyperactivityScore)
                .impulsivityScore(impulsivityScore)
                .organizationScore(organizationScore)
                .build();

        return adhdScoreRepository.save(score);
    }
    private int calculateCategoryScore(List<UserAnswer> answers, String category) {
        return answers.stream()
                .filter(a -> a.getQuestion().getCategory().equals(category))
                .mapToInt(UserAnswer::getAnswer)
                .sum();
    }
    public String determineRiskLevel(int totalScore) {
        if (totalScore <= 9) return "정상";
        if (totalScore <= 19) return "경계";
        if (totalScore <= 29) return "주의 필요";
        if (totalScore <= 39) return "개선 필요";
        return "전문가 상담 권장";
    }
}
