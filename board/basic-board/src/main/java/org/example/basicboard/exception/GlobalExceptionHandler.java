package org.example.basicboard.exception;

import org.example.basicboard.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// 모든 컨트롤러에 공통으로 적용되는 보조 클래스임을 선언하는 어노테이션
// 특정 컨트롤러 한 개가 아니라, 애프리케이션의 모든 @Controller/@RestController에서 발생하는 예외를 가로챈다

// 전역 예외 처리
// 예외가 터질 때마다 컨트롤러 안에서 try-catch로 일일이 잡으면, 컨트롤러마다 같은 코드가 반복된다.
// 핵심 로직과 예외 처리 코드가 뒤섞여서 지저분해지고, 응답 형태도 재각각이 되기 쉽다
// 그래서 예외 처리라는 공통 관심사를 한 곳에 모아두고, 컨트롤러/서비스는 예외를 던지기만 하게 만든다.
// 컨트롤러는 성공 흐름에만 집중하고 예외 -> 응답 변환은 전부 이 클래스가 책임진다

// 전체 흐름
//   서비스: throw new DuplicateUserIdException("이미 존재하는 아이디입니다.")
//     -> (컨트롤러는 잡지 않고 그대로 위로 전파됨)
//        -> 여기 GlobalExceptionHandler 가 가로챔
//           -> 상태코드(409) + ErrorResponseDto(JSON) 로 변환해 응답
//              -> signUp.js 의 error 콜백이 message 를 꺼내 화면에 표시

// 예외 처리도 공통 관심사인데 왜 AOP가 아니라 RestController를 쓸까?
// 내부적으로는 AOP와 같은 공통 관심사 분리 아이디어 위에 있다
// 범용 AOP를 직접 만들것이냐 vs 웹 예외 처리에 특화된 전용 도구를 쓸 것이냐의 문제이다

// AOP는 컨트롤러 메서드 호출 그 순간만 감쌀 수 있다
// @Around는 컨트롤러 메서드가 실제로 실행되는 지점만 try-catch로 감싼다.
// 하지만 웹 예외는 메서드 바깥에서도 터진다
// 이런 예외는 컨트롤러 메서드가 호출되기도 전에 발생해서 AOP 포인트 컷에 잡히지 않는다.
// @RestControllerAdvice는 스프링 MVC의 예외 처리 파이프라인에 연결되어 이런 프레임워크 단계의 예외까지 잡아준다

// 응답 만들기를 프레임워크가 대신 해준다
// AOP로 직접 하면 에외를 잡은 뒤 상태코트 세팅, JSON 직렬화, Content-type 을 전부 짜야한다.
// @RestControllerAdvice는 ResponseEntity / 메시지 컨버터(DTO) 자동 JSON 변환 / 예외 타입 매핑을 제공한다

// 예외 타입별 분기가 선언적이다
// @ExpectionHandler로 이 예외는 이 매서드가 어노테이션으로 나눈다
// AOP로 하면 if (e instanse of ...) 분기를 직접 나열해야 해서 읽기 어렵고 유지보수가 나쁘다

// 한 줄 요약: 도구를 목적에 맞게
// 로깅/실행시간 측정/트랜잭션 처럼 "무엇에나 끼워 넣는 범용 작업" -> AOP (@Aspect)  [LoggingAspect 참고]
// 예외를 적절한 HTTP 응답으로 바꾸는 "웹 특화 작업" -> @RestControllerAdvice (이 클래스)
// (억지로 AOP 로 예외 처리도 되긴 하지만, 위 (1)(2) 를 전부 직접 만들어야 해서 손해다)

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler : 어떤 예외를 처리할지 지정한다.
    // 괄호 안에 적은 예외 타입이 발생하면, 스프링이 이 메서드를 자동으로 호출한다
    // 메서드 파라미터로 그 예외 객체를 받아, 메시지 등 상세 정보를 꺼내 쓸 수 있다

    // ResponseEntity<T>
    // HTTP 응답 전체를 표현하는 객체이다. 응답 본문뿐 아니라 상태 코드와 헤더까지 직접 지정할 수 있다.
    // 단순히 DTO만 반환하면 상태 코드가 항상 200(OK)으로 나가버린다
    // 에러 상황에서는 상태 코드를 4xx/4xx 등으로 바꿔야 하므로 ResponseEntity를 사용한다
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> duplicateUserIdException(DuplicateUserIdException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> boardNotFoundException(BoardNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 최후의 보루 핸들러 : 위에서 처리하지 못한 모든 예외를 잡는다
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"));
    }
}
