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
