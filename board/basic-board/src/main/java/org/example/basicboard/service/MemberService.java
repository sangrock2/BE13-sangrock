package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Member;
import org.example.basicboard.domain.repository.MemberRepository;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        // 아이디 중복 체크
        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }

        memberRepository.save(memberMapper.toEntity(dto));
    }

    // Optional<Member> : NPE(NullPointerException) 예방
    // 예전에는 값이 없음을 null 로 표현했는데 null을 깜빡하고 그냥 쓰면 실행중에 NPE가 터졌다.
    // 반환 타입만 봐서는 null이 올 수 있는지 알 수가 없어 실수하기가 쉽다

    // Optional : 값이 없을 수도 있다를 타입으로 알려주는 매퍼
    // 반환 타입이 Optional이면 값이 없을 수 있으니 처리해라라고 컴파일 단계에서 강제한다
    // 즉 없을 수 있음을 문서가 아니라 타입으로 표현해 실수를 막는 장치다

    // 매퍼를 여는 주요 매서드
    // IsPresent() / isEmpty() : 값이 있는지/없는지 Bolean으로 확인
    // orElse(기본값) : 있으면 그 값, 없으면 기본값
    // orElseGet(함수) : 있으면 그 값, 없으면 함수를 실행
    // - map(함수) : 값이 있으면 다른 값으로 변환, 없으면 그대로 empty
    // - filter(조건) : 값이 있고 조건을 만족하면 유지, 아니면 empty

    // 세 가지 상태
    // Optional.of(값) : 값이 확실히 있을때 (값이 null 이라면 즉시 예외)
    // Optional.empty() : 빈 상자
    // Optional.ofNullable(값) : 값이 null 일 수도 있을때 (null 이면 empty, 아니면 of)

    // 주의 (자주 하는 실수)
    //   - get() 남발 금지: 비어있으면 예외다. orElse* 계열로 안전하게 꺼낸다
    //   - if(opt.isPresent()) opt.get() 패턴은 사실상 null 체크와 다를 게 없다 -> map/orElseGet 으로 흘려보내자
    //   - 보통 "반환 타입"에만 쓴다. 필드나 파라미터 타입으로는 잘 쓰지 않는다 (설계 관례)

    public Optional<Member> login(LoginRequestDto dto) {
        // findByUserId 는 Optional<Member> 를 돌려준다 (회원이 있을 수도, 없을 수도)

        // .filter( member -> 조건 ) 의 동작:
        // Optional 안에 값이 "있고" + 람다가 true  -> 그 값을 그대로 유지
        // 값이 "없거나" + 람다가 false -> 빈 Optional(Optional.empty)로 만든다
        // => "아이디로 찾은 회원이 있고, 비밀번호까지 일치하면 남기고, 아니면 비운다" = 로그인 성공/실패 판정

        // member -> member.getPassword().equals(...) 가 바로 람다식(이름 없는 함수)이다
        // member : 입력 파라미터 (Optional 안에 든 Member)
        // -> : "이것을 받아서 ~를 반환한다"
        // member.getPassword().equals(..): 반환값 (boolean). 비밀번호가 같으면 true

        // 만약 람다(와 Optional)를 쓰지 않았다면, 아래처럼 풀어 쓴 것과 같다:
        // 1) 아이디로 회원을 조회한다 (없으면 null 이 나오도록 orElse(null) 사용)
        // Member member = memberRepository.findByUserId(request.getUsername()).orElse(null);
        // 2) 회원이 존재하고(null 아님) + 비밀번호가 일치하면 -> 로그인 성공
        // if (member != null && member.getPassword().equals(request.getPassword())) {
        //   return Optional.of(member); // 성공: 회원을 담아 반환
        // }
        // 3) 아이디가 없거나 비밀번호가 틀리면 -> 로그인 실패
        // return Optional.empty(); // 실패: 빈 Optional 반환
        // => 위 if 분기(널 체크 + 비밀번호 비교)를 .filter(람다) 한 줄로 압축한 것이 아래 코드다
        return memberRepository.findByUserId(dto.getUsername())
                .filter(entity -> entity.getPassword().equals(dto.getPassword()));
    }
}
