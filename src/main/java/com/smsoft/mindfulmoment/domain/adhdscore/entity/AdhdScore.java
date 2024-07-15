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

    private void calculateIndicators() {
        // 각 지표 계산 로직 구현
        this.calmness = (hyperactivityScore + impulsivityScore) / 2;
        this.concentration = attentionDeficitScore;
        this.timeManagement = organizationScore;
        this.decisionMaking = impulsivityScore;
        this.emotionalControl = (hyperactivityScore + impulsivityScore) / 2;
        this.taskCompletion = (attentionDeficitScore + organizationScore) / 2;
    }

    public void updateScores(Integer attentionDeficitScore, Integer hyperactivityScore,
                             Integer impulsivityScore, Integer organizationScore) {
        this.attentionDeficitScore = attentionDeficitScore;
        this.hyperactivityScore = hyperactivityScore;
        this.impulsivityScore = impulsivityScore;
        this.organizationScore = organizationScore;
        this.totalScore = calculateTotalScore();
        calculateIndicators();
    }
}
