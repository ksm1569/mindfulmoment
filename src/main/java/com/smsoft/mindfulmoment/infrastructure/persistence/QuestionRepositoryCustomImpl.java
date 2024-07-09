package com.smsoft.mindfulmoment.infrastructure.persistence;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smsoft.mindfulmoment.domain.question.dto.QuestionDto;
import com.smsoft.mindfulmoment.domain.question.entity.QQuestion;
import com.smsoft.mindfulmoment.domain.question.repository.QuestionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestionDto> findRandomQuestionsForAllCategories() {
        QQuestion question = QQuestion.question;
        List<String> categories = Arrays.asList("ATTENTION", "HYPERACTIVITY", "IMPULSIVITY", "ORGANIZATION");
        List<Long> randomIds = new ArrayList<>();

        for (String category : categories) {
            OrderSpecifier<?> randomOrder = Expressions.numberTemplate(Double.class, "RAND()").asc();

            List<Long> ids = queryFactory
                    .select(question.id)
                    .from(question)
                    .where(question.category.eq(category))
                    .orderBy(randomOrder)
                    .limit(5)
                    .fetch();

            randomIds.addAll(ids);
        }

        return queryFactory
                .select(Projections.constructor(QuestionDto.class,
                    question.id,
                    question.text,
                    question.category
                ))
                .from(question)
                .where(question.id.in(randomIds))
                .fetch();
    }
}
