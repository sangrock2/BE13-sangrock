package org.example.spring_mission_join.mapper;

import org.example.spring_mission_join.domain.entity.Member;
import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toEntity(MemberJoinRequestDto memberJoinRequestDto) {
        return Member.builder()
                .userId(memberJoinRequestDto.getUserId())
                .password(memberJoinRequestDto.getPassword())
                .userName(memberJoinRequestDto.getUserName())
                .build();
    }
}
