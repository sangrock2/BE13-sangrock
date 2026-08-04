package org.example.token.dto;

import lombok.Getter;
import org.example.token.domain.entity.Role;
import org.example.token.domain.entity.User;

@Getter
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Role role;

    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role != null ? role : Role.ROLE_USER)
                .build();
    }
}
