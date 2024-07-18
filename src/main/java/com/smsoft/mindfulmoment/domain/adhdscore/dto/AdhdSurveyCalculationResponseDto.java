package com.smsoft.mindfulmoment.domain.adhdscore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdhdSurveyCalculationResponseDto {
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
}
