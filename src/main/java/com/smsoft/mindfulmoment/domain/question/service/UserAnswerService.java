package com.smsoft.mindfulmoment.domain.question.service;

import com.smsoft.mindfulmoment.domain.adhdscore.dto.SurveyAnswerDto;
import com.smsoft.mindfulmoment.domain.common.exception.BusinessException;
import com.smsoft.mindfulmoment.domain.common.exception.ErrorCode;
import com.smsoft.mindfulmoment.domain.question.entity.Question;
import com.smsoft.mindfulmoment.domain.question.entity.UserAnswer;
import com.smsoft.mindfulmoment.domain.question.repository.QuestionRepository;
import com.smsoft.mindfulmoment.domain.question.repository.UserAnswerRepository;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void saveUserAnswers(User user, List<SurveyAnswerDto> answers) {
        List<UserAnswer> userAnswers = answers.stream()
                .map(answerDto -> {
                    Question question = questionRepository.findById(answerDto.getQuestionId())
                            .orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_NOT_EXISTS));
                    return UserAnswer.create(user, question, answerDto.getAnswerValue());
                })
                .collect(Collectors.toList());

        userAnswerRepository.saveAll(userAnswers);
    }
}
