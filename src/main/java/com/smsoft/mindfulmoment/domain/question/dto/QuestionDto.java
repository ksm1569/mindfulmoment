package com.smsoft.mindfulmoment.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class QuestionDto {
    private Long id;
    private String text;
    private String category;
}
