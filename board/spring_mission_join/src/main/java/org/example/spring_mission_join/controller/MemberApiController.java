package org.example.spring_mission_join.controller;

import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.example.spring_mission_join.dto.MemberJoinResponseDto;
import org.example.spring_mission_join.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        memberService.join(memberJoinRequestDto);

        return new MemberJoinResponseDto("/members/login");
    }

}
