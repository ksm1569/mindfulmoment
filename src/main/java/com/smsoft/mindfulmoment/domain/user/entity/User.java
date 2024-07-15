package com.smsoft.mindfulmoment.domain.user.entity;

import com.smsoft.mindfulmoment.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "unique_identifier", unique = true)
    private String uniqueIdentifier;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiry_date")
    private LocalDateTime refreshTokenExpiryDate;

    @Column(name = "last_survey_date")
    private LocalDateTime lastSurveyDate;

    @Builder
    public User(String email, String password, String name, String imageUrl, AuthProvider provider,
                String providerId, String uniqueIdentifier) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime expiryDate) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpiryDate = expiryDate;
    }

    public void updateLastSurveyDate(LocalDateTime surveyDate) {
        this.lastSurveyDate = surveyDate;
    }

    public void updateProfile(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
