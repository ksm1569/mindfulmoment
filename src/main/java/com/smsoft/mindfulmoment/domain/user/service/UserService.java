package com.smsoft.mindfulmoment.domain.user.service;

import com.smsoft.mindfulmoment.domain.user.dto.UserDto;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import com.smsoft.mindfulmoment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from);
    }

    @Transactional
    public UserDto updateUserProfile(Long userId, String name, String imageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.updateProfile(name, imageUrl);
        return UserDto.from(user);
    }

    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken, LocalDateTime expiryDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.updateRefreshToken(refreshToken, expiryDate);
    }

    @Transactional(readOnly = true)
    public Optional<String> getRefreshTokenForUser(Long userId) {
        return userRepository.findById(userId)
                .map(User::getRefreshToken);
    }
}