package org.example.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.oauth2.config.oauth2.AuthProvider;

@Getter
@AllArgsConstructor
public class SignupPayloadDto {
    private final AuthProvider provider;
    private final String providerId;
    private final String email;
    private final String name;
}
