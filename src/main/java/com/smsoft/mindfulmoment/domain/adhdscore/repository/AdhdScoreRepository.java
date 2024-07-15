package com.smsoft.mindfulmoment.domain.adhdscore.repository;

import com.smsoft.mindfulmoment.domain.adhdscore.entity.AdhdScore;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdhdScoreRepository extends JpaRepository<AdhdScore, Long> {
    Optional<AdhdScore> findByUser(User user);
}
