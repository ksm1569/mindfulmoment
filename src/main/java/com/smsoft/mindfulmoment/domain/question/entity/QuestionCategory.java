package com.smsoft.mindfulmoment.domain.question.entity;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.SurveyAnswerDto;
import com.smsoft.mindfulmoment.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_categories")
@Entity
public class QuestionCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    public int calculateCategoryScore(List<SurveyAnswerDto> answers) {
        return answers.stream()
                .filter(answer -> this.code.equals(answer.getCategory()))
                .mapToInt(SurveyAnswerDto::getAnswerValue)
                .sum();
    }
}
