package com.smsoft.mindfulmoment.domain.adhdscore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AdhdSurveyCalculationRequestDto {
    private List<SurveyAnswerDto> answers;
}
