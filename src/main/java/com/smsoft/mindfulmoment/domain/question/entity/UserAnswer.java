package com.smsoft.mindfulmoment.domain.question.entity;

import com.smsoft.mindfulmoment.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_answers")
@Entity
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private Integer answer;

    @Builder
    public UserAnswer(User user, Question question, Integer answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public static UserAnswer create(User user, Question question, Integer answer) {
        return UserAnswer.builder()
                .user(user)
                .question(question)
                .answer(answer)
                .build();
    }
}
