package org.example.basicboard.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.LoginResponseDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.dto.MemberJoinResponseDto;
import org.example.basicboard.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto dto) {
        memberService.join(dto);

        return new MemberJoinResponseDto("/members/login");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto, HttpSession session) {
        return memberService.login(dto).map(member -> {
            session.setAttribute("userId", member.getUserId());
            session.setAttribute("userName", member.getUserName());

            return LoginResponseDto.success();
        }).orElseGet(LoginResponseDto::fail);
    }
}
