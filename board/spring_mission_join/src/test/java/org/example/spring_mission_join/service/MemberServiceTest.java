package org.example.spring_mission_join.service;

import org.example.spring_mission_join.domain.entity.Member;
import org.example.spring_mission_join.domain.repository.MemberRepository;
import org.example.spring_mission_join.dto.LoginRequestDto;
import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.example.spring_mission_join.exception.DuplicateUserIdException;
import org.example.spring_mission_join.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();
    }

    @Test
    void login_success() {
        given(memberRepository.findByUserId("test")).willReturn(Optional.of(member));

        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("test");
        dto.setPassword("1234");

        Optional<Member> result = memberService.login(dto);

        assertThat(result).isPresent();
        assertThat(result.get().getUserName()).isEqualTo("홍길동");
    }

    @Test
    void login_fail_password() {
        given(memberRepository.findByUserId("test")).willReturn(Optional.of(member));

        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("test");
        dto.setPassword("9999");

        Optional<Member> result = memberService.login(dto);

        assertThat(result).isNotPresent();
    }

    @Test
    void login_not_exist_user() {
        given(memberRepository.findByUserId("nobody")).willReturn(Optional.empty());

        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("nobody");
        dto.setPassword("9999");

        Optional<Member> result = memberService.login(dto);

        assertThat(result).isNotPresent();
    }

    @Test
    void join_success() {
        MemberJoinRequestDto dto = new MemberJoinRequestDto();

        dto.setUserId("test");
        dto.setPassword("1234");
        dto.setUserName("홍길동");

        given(memberRepository.existsByUserId(dto.getUserId())).willReturn(false);
        given(memberMapper.toEntity(dto)).willReturn(member);

        memberService.join(dto);

        // verify(memberRepository).save(member);
        then(memberRepository).should(times(1)).save(member);
    }

    @Test
    void join_duplicate_user() {
        MemberJoinRequestDto dto = new MemberJoinRequestDto();

        dto.setUserId("test");
        dto.setPassword("1234");
        dto.setUserName("홍길동");

        given(memberRepository.existsByUserId(dto.getUserId())).willReturn(true);

        assertThatThrownBy(() -> memberService.join(dto))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessageContaining("already exists user id");

        then(memberRepository).should(never()).save(any(Member.class));
    }
}
