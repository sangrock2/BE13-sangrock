package org.example.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private boolean successed;
    private String url;
    private String message;

    public static LoginResponseDto success() {
        return new LoginResponseDto(true, "/", "로그인에 성공했습니다.");
    }

    public static LoginResponseDto fail() {
        return new LoginResponseDto(false, null, "로그인에 실패했습니다.");
    }
}
