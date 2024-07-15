package com.smsoft.mindfulmoment.infrastructure.security.userdetails;

import com.smsoft.mindfulmoment.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {

    @Getter
    private final UserDto userDto;
    private final Collection<? extends GrantedAuthority> authorities;
    @Setter
    private Map<String, Object> attributes;

    public UserPrincipal(UserDto userDto) {
        this.userDto = userDto;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public static UserPrincipal create(UserDto userDto) {
        return new UserPrincipal(userDto);
    }

    public static UserPrincipal create(UserDto userDto, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(userDto);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(userDto.getId());
    }
}
