package com.smsoft.mindfulmoment.infrastructure.security.oauth2;

import com.smsoft.mindfulmoment.domain.user.dto.UserDto;
import com.smsoft.mindfulmoment.domain.user.entity.AuthProvider;
import com.smsoft.mindfulmoment.domain.user.entity.User;
import com.smsoft.mindfulmoment.domain.user.repository.UserRepository;
import com.smsoft.mindfulmoment.infrastructure.security.oauth2.user.OAuth2UserInfo;
import com.smsoft.mindfulmoment.infrastructure.security.userdetails.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        String uniqueIdentifier = registrationId + "_" + oAuth2UserInfo.getId();

        User user = userRepository.findByUniqueIdentifier(uniqueIdentifier)
                .map(existingUser -> updateExistingUser(existingUser, oAuth2UserInfo))
                .orElseGet(() -> createUser(oAuth2UserInfo, registrationId, uniqueIdentifier));

        return UserPrincipal.create(UserDto.from(user), oAuth2User.getAttributes());
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.updateProfile(oAuth2UserInfo.getName(), oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo, String registrationId, String uniqueIdentifier) {
        User user = User.builder()
                .provider(AuthProvider.valueOf(registrationId.toUpperCase()))
                .providerId(oAuth2UserInfo.getId())
                .uniqueIdentifier(uniqueIdentifier)
                .name(oAuth2UserInfo.getName())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .email(oAuth2UserInfo.getImageUrl())
                .build();

        return userRepository.save(user);
    }
}
