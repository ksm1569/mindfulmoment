package com.smsoft.mindfulmoment.domain.user.dto;

import com.smsoft.mindfulmoment.domain.user.entity.AuthProvider;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private final Long id;
    private final String email;
    private final String name;
    private final String imageUrl;
    private final AuthProvider provider;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .provider(user.getProvider())
                .build();
    }
}
