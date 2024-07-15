package com.smsoft.mindfulmoment.infrastructure.persistence.question;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.mindfulmoment.domain.question.dto.QuestionDto;
import com.smsoft.mindfulmoment.domain.question.entity.QQuestion;
import com.smsoft.mindfulmoment.domain.question.entity.QQuestionCategory;
import com.smsoft.mindfulmoment.domain.question.repository.QuestionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestionDto> findAllQuestions() {
        QQuestion question = QQuestion.question;
        QQuestionCategory category = QQuestionCategory.questionCategory;

        return queryFactory
                .select(Projections.constructor(QuestionDto.class,
                        question.id,
                        question.text,
                        category.code.as("category")))
                .from(question)
                .join(question.category, category)
                .fetch();
    }
}
