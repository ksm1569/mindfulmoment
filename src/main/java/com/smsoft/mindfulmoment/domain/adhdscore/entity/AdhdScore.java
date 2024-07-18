package com.smsoft.mindfulmoment.domain.adhdscore.entity;

import com.smsoft.mindfulmoment.domain.common.BaseTimeEntity;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "adhd_scores")
@Entity
public class AdhdScore extends BaseTimeEntity {
    private static final int MAX_CATEGORY_SCORE = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer attentionDeficitScore; // 주의력 결핍 점수
    private Integer hyperactivityScore;    // 과잉행동 점수
    private Integer impulsivityScore;      // 충동성 점수
    private Integer organizationScore;     // 조직화 점수
    private Integer totalScore;            // 총점

    private Integer calmness;          // 차분함
    private Integer concentration;     // 집중력
    private Integer timeManagement;    // 시간 관리
    private Integer decisionMaking;    // 의사 결정
    private Integer emotionalControl;  // 감정 조절
    private Integer taskCompletion;    // 과제 완수

    @Builder
    public AdhdScore(User user, Integer attentionDeficitScore, Integer hyperactivityScore,
                     Integer impulsivityScore, Integer organizationScore) {
        this.user = user;
        this.attentionDeficitScore = attentionDeficitScore;
        this.hyperactivityScore = hyperactivityScore;
        this.impulsivityScore = impulsivityScore;
        this.organizationScore = organizationScore;
        this.totalScore = calculateTotalScore();
        calculateIndicators();
    }

    private Integer calculateTotalScore() {
        return attentionDeficitScore + hyperactivityScore + impulsivityScore + organizationScore;
    }

    /**
     * 1. 차분함(calmness) : 과잉행동과 충동성이 낮을수록 차분함이 높다.
     * 2. 집중력(concentration) : 주의력 결핍이 낮을수록 집중력이 높다.
     * 3. 시간관리(timeManagement) : 조직화 문제가 적을수록 시간 관리 능력이 좋다.
     * 4. 의사결정(decisionMaking) : 충동성이 낮을수록 의사 결정 능력이 좋다.
     * 5. 감정 조절(emotionalControl) : 과잉행동과 충동성이 낮을수록 감정 조절 능력이 좋다.
     * 6. 과제 완수(taskCompletion) : 주의력 문제와 조직화 문제가 적을수록 과제 완수 능력이 좋다.
     * */
    private void calculateIndicators() {
        this.calmness = normalizeScore(MAX_CATEGORY_SCORE * 2 - (hyperactivityScore + impulsivityScore));
        this.concentration = normalizeScore(MAX_CATEGORY_SCORE - attentionDeficitScore);
        this.timeManagement = normalizeScore(MAX_CATEGORY_SCORE - organizationScore);
        this.decisionMaking = normalizeScore(MAX_CATEGORY_SCORE - impulsivityScore);
        this.emotionalControl = normalizeScore(MAX_CATEGORY_SCORE * 2 - (hyperactivityScore + impulsivityScore));
        this.taskCompletion = normalizeScore(MAX_CATEGORY_SCORE * 2 - (attentionDeficitScore + organizationScore));
    }

    /**
     * 수치 정규화 메서드
     * */
    private int normalizeScore(int score) {
        return Math.min(Math.max(score * 100 / MAX_CATEGORY_SCORE, 0), 100);
    }

    public int getNormalizedAttentionDeficitScore() {
        return normalizeScore(attentionDeficitScore);
    }
    public int getNormalizedHyperactivityScore() {
        return normalizeScore(hyperactivityScore);
    }
    public int getNormalizedImpulsivityScore() {
        return normalizeScore(impulsivityScore);
    }
    public int getNormalizedOrganizationScore() {
        return normalizeScore(organizationScore);
    }
    public int getNormalizedTotalScore() {
        return normalizeScore(totalScore / 4);
    }

}
