package org.example.spring_mission_join.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_mission_join.domain.repository.MemberRepository;
import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.example.spring_mission_join.exception.DuplicateUserIdException;
import org.example.spring_mission_join.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto memberJoinRequestDto) {
        if (memberRepository.existsByUserId(memberJoinRequestDto.getUserId())) {
            throw new DuplicateUserIdException("already exists user id");
        }

        memberRepository.save(memberMapper.toEntity(memberJoinRequestDto));
    }


}
