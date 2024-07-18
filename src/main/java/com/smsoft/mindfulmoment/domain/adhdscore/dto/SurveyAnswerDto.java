package com.smsoft.mindfulmoment.domain.adhdscore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SurveyAnswerDto {
    private Long questionId;
    private Integer answerValue;
    private String category;
}
