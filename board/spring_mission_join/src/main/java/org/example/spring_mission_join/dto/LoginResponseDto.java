package org.example.spring_mission_join.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseDto {
    private boolean loggedIn;
    private String url;
    private String message;

    public static LoginResponseDto success(){
        return new LoginResponseDto(true, "/", "login success");
    }

    public static LoginResponseDto fail(){
        return new LoginResponseDto(false, null, "login fail");
    }
}
