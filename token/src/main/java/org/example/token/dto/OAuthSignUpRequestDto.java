package org.example.oauth2.dto;

import lombok.Getter;
import org.example.oauth2.domain.entity.Role;

@Getter
public class OAuthSignUpRequestDto {
    private String signupToken;
    private Role role;
}
