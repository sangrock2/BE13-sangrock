package org.example.formlogin.controller;

import lombok.RequiredArgsConstructor;
import org.example.formlogin.dto.SignUpRequestDto;
import org.example.formlogin.dto.SignUpResponseDto;
import org.example.formlogin.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp( signUpRequestDto );

        return new SignUpResponseDto( "/users/login" );
    }
}
