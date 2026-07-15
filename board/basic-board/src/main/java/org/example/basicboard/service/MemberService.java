package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.basicboard.domain.entity.Member;
import org.example.basicboard.domain.repository.MemberRepository;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// * @Slf4j 로 서비스 레벨 로깅하기(lombok)
// 로그 레벨 5단계 - "이 메시지가 얼마나 중요한가"의 등급
// trace < debug < info < warn < error
// - trace/debug : 개발 중 흐름 추적용 (운영 기본 설정에선 안 찍힘)
// - info : 의미 있는 비즈니스 이벤트(가입 완료, 글 등록 등 "정상인데 기록할 가치가 있는 일")
// - warn : 이상하지만 서비스는 계속되는 상황(로그인 실패, 중복 가입 시도 등)
// - error : 예상 못한 실패 (500 계열)

// {} 플레이스홀더 - "문자열 + 값" 대신 반드시 이걸 쓴다.
// log.debug("가입 요청 : " + dto.getUserId()); // (X) - 레벨이 꺼져 있어도 문자열 연결 비용은 발생
// log.debug("가입 요청 : userId={}", dto.getUserId()); // (O) - 실제로 찍힐 때만 조립(지연 평가)

// 민감한 정보는 로그에 남기지 않는다.
// 객체를 통째로 찍으면 그 안의 값들이 그대로 로그에 남는다.
// 로그 파일은 오래 보관되고 여러 사람이 보므로, 민감한 정보는 제외해야 한다.

// AOP와의 역할 분담
// AOP : 무엇이 호출됐고 몇 ms 걸렸나 (기계적/공통 - 모든 요청에 일괄)
// 서비스 로그 : 비즈니스에 무슨 일이 있었나 (선별적/의미 - 분기점, 상태 변화, 실패 지점만)
// 그래서 서비스에서는 "메서드 시작/끝" 로그를 찍지 않는다.

// Transactional(readOnly = true)
// 이 트랜잭션은 데이터를 안 바꾼다."라고 JPA한테 알려준다. 이 트랜잭션에서 조회만 하겠다는 힌트 -> 최적화
// 하이버네이트가 변경감지를 위한 스냅샷을 안 만들어 메모리/성능에 유리

@Slf4j
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
            log.warn("Member registration failed: duplicate userId={}", dto.getUserId());

            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }

        memberRepository.save(memberMapper.toEntity(dto));
        log.info("Member registered: userId={}, userName={}", dto.getUserId(), dto.getUserName());
    }

    // Optional : 값이 없을 수도 있다를 타입으로 알려주는 매퍼 / NPE(NullPointerException) 예방
    // null을 깜빡하고 그냥 쓰면 실행중에 NPE가 터진다. 반환 타입만 봐서는 null이 올 수 있는지 알 수가 없어 실수하기가 쉽다
    // 반환 타입이 Optional이면 값이 없을 수 있으니 처리해라라고 컴파일 단계에서 강제한다
    // 없을 수 있음을 문서가 아니라 타입으로 표현해 실수를 막는 장치다
    // IsPresent() / isEmpty() : 값이 있는지/없는지 Bolean으로 확인
    // orElse(기본값) : 있으면 그 값, 없으면 기본값
    // orElseGet(함수) : 있으면 그 값, 없으면 함수를 실행
    // map(함수) : 값이 있으면 다른 값으로 변환, 없으면 그대로 empty
    // filter(조건) : 값이 있고 조건을 만족하면 유지, 아니면 empty
    // Optional.of(값) : 값이 확실히 있을때 (값이 null 이라면 즉시 예외)
    // Optional.empty() : 빈 상자
    // Optional.ofNullable(값) : 값이 null 일 수도 있을때 (null 이면 empty, 아니면 of)

    // 주의 (자주 하는 실수)
    // get() 남발 금지: 비어있으면 예외다. orElse* 계열로 안전하게 꺼낸다
    // if(opt.isPresent()) opt.get() 패턴은 사실상 null 체크와 다를 게 없다 -> map/orElseGet 으로 흘려보내자
    // 보통 "반환 타입"에만 쓴다. 필드나 파라미터 타입으로는 잘 쓰지 않는다 (설계 관례)

    public Optional<Member> login(LoginRequestDto dto) {
        // findByUserId 는 Optional<Member> 를 돌려준다 (회원이 있을 수도, 없을 수도)

        // .filter( member -> 조건 ) 의 동작:
        // Optional 안에 값이 있고 + 람다가 true -> 그 값을 그대로 유지
        // 값이 없거나 + 람다가 false -> 빈 Optional(Optional.empty)로 만든다
        // 아이디로 찾은 회원이 있고, 비밀번호까지 일치하면 남기고, 아니면 비운다 = 로그인 성공/실패 판정

        // member -> member.getPassword().equals(...) 가 바로 람다식(이름 없는 함수)이다
        // member.getPassword().equals(..): 반환값 (boolean). 비밀번호가 같으면 true

        Optional<Member> result = memberRepository.findByUserId(dto.getUsername())
                .filter(entity -> entity.getPassword().equals(dto.getPassword()));

        if (result.isEmpty()) {
            log.warn("Login failed: userId={}", dto.getUsername());
        } else {
            log.info("Login succeeded: username={}", dto.getUsername());
        }

        return result;
    }
}
