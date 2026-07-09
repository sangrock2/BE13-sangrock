package org.example.basicboard.service;

import org.example.basicboard.domain.entity.Member;
import org.example.basicboard.domain.repository.MemberRepository;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

// Mockito
// 가짜 객체를 쉽게 만들어주는 자바 테스트 라이브러리
// 단위 테스트 대상 하나가 제대로 동작하는지 보고 싶을때 사용
// MemberService는 MemberRepository에 의존한다. 진짜 리포지토리를 쓰면 DB가 떠 있어야하고 느리고 DB/REPOSITORY 버그가 같이 생긴다
// 그래서 리포지토리를 가짜로 바꿔 그 행동을 내가 정해 놓고 서비스 로직만 검증한다

// 스터빙 : 이렇게 호출하면 이 값을 돌려줘라
// given(repo.existByUserId("newbie")).willReturn(false) : 특정인자 -> false 반환하도록
// given(repo.existByUserId("ㅅㄷㄴㅅ")).willReturn(Optional.of(member) : 회원을 담아 반환

// * 검증 - "그 메서드가 호출됐는지" (verify)  : 주로 반환값 없는 void 로직 확인에 쓴다
// - verify(repo).save(entity);              // save 가 "그 엔티티로" 정확히 1번 호출됐어야 한다 (기본=1번)
// - verify(repo, times(2)).save(any());     // 정확히 2번
// - verify(repo, never()).save(any());      // 한 번도 호출되면 안 된다

// * 인자 매처 - "구체값 대신 '아무거나' 로 느슨하게" (any, eq ...)
// - any()          : 아무 값이나 (타입 무관)         예) verify(repo).save(any());
// - anyString()    : 아무 문자열이나
// - eq("hong")     : 정확히 "hong" 인 인자
// - 주의: 한 메서드의 인자 중 하나라도 매처(any 등)를 쓰면, 나머지 인자도 전부 매처로 써야 한다
// 예) verify(repo).method(eq("hong"), any());   // "hong" 은 그냥 값이 아니라 eq() 로 감싼다

// # 핵심 아이디어: MemberService 만 진짜 객체로 쓰고, 그것이 의존하는 것들(리포지토리/매퍼)은 "가짜(Mock)"로 바꾼다
//   - 진짜 DB 리포지토리를 쓰면 DB 가 떠 있어야 하고 느리다. 우리는 "서비스 로직" 만 보고 싶다
//   - 그래서 리포지토리를 Mock 으로 두고 "이 메서드는 이런 값을 돌려준다고 치자" 라고 우리가 지정한다
//   - 스프링을 아예 띄우지 않으므로 매우 빠르다 (@SpringBootTest 없음에 주목)

// # 참고: 서비스에 붙은 클래스 레벨 @Transactional 은 여기선 동작하지 않는다
//         (트랜잭션은 스프링이 프록시로 걸어주는 기능인데, 지금은 스프링을 안 띄우고 new 로 만든 순수 객체라서)

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("로그인 - 아이디가 있고 비밀번호가 일치하면 회원을 담은 Optional을 반환한다")
    void login_아이디와_비밀번호가_맞으면_회원을_반환한다() {
        // given - "test/1234" 회원이 DB에 있다고 가정
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();

        given(memberRepository.findByUserId("test")).willReturn(Optional.of(member));

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("test");
        requestDto.setPassword("1234");

        Optional<Member> result = memberService.login(requestDto);

        assertThat(result).isPresent();
        assertThat(result.get().getUserName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("로그인 - 비밀번호가 틀리면 Optional을 반환한다")
    void login_비밀번호가_틀리면_Optional() {
        // given - "test/1234" 회원이 DB에 있다고 가정
        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();

        given(memberRepository.findByUserId("test")).willReturn(Optional.of(member));

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("test");
        requestDto.setPassword("9999");

        Optional<Member> result = memberService.login(requestDto);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("로그인 - 아이디가 없으면 Optional을 반환한다")
    void login_아이디가_없으면_Optional() {
        given(memberRepository.findByUserId("nobody")).willReturn(Optional.empty());

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("nobody");
        requestDto.setPassword("9999");

        Optional<Member> result = memberService.login(requestDto);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("회원가입 - 아이디가 중복이 아니면 파일을 저장한다")
    void join_중복이_아니면_저장한다() {
        MemberJoinRequestDto requestDto = new MemberJoinRequestDto();

        requestDto.setUserId("test");
        requestDto.setPassword("1234");
        requestDto.setUserName("홍길동");

        Member member = Member.builder()
                .userId("test")
                .password("1234")
                .userName("홍길동")
                .build();

        given(memberRepository.existsByUserId("test")).willReturn(false);
        given(memberMapper.toEntity(requestDto)).willReturn(member);

        memberService.join(requestDto);

        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("회원가입 - 아이디가 이미 있으면 DuplicateUserIdException을 던지고 저장하지 않는다.")
    void join_중복이면_예외() {
        // given
        MemberJoinRequestDto requestDto = new MemberJoinRequestDto();
        requestDto.setUserId("test");
        requestDto.setPassword("1234");
        requestDto.setUserName("홍길동");

        given( memberRepository.existsByUserId("test") ).willReturn( true );

        // when & then
        assertThatThrownBy(() -> memberService.join(requestDto))
                .isInstanceOf(DuplicateUserIdException.class)
                .hasMessageContaining("[회원가입] 이미 존재하는 아이디입니다.");

        // verify(memberRepository, never()).save( any() );
    }

}