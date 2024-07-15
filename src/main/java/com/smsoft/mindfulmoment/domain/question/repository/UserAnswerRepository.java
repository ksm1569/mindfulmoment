package com.smsoft.mindfulmoment.domain.question.repository;

import com.smsoft.mindfulmoment.domain.question.entity.UserAnswer;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByUser(User user);
}