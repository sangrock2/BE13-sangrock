package org.example.spring_mission_join.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_mission_join.domain.entity.Member;
import org.example.spring_mission_join.domain.repository.MemberRepository;
import org.example.spring_mission_join.dto.LoginRequestDto;
import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.example.spring_mission_join.exception.DuplicateUserIdException;
import org.example.spring_mission_join.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Optional<Member> login(LoginRequestDto loginRequestDto) {
        return memberRepository.findByUserId(loginRequestDto.getUsername()).filter(entity -> entity.getPassword().equals(loginRequestDto.getPassword()));
    }


}
