package org.example.basicboard.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

// @Aspect
// 이 클래스는 공통 기능(횡단 관심사)를 모아둔 Aspect다라고 선언
// 스프링 AOP가 이 클래스 안의 포인트컷/어드바이스를 인식한다.
// AOP 규칙을 담고 있다는 표시일 뿐, 스프링이 관리하는 빈으로 등록해주지 않는다
// 스프링 컨테이너에 빈으로 등록해야, 스프링이 이 Aspect를 찾아서 실제로 적용한다

// 표현식 해석
// execution : 메서드 실행 지점을 대상으로 한다는 지시
// 가장 앞 * : 반환 타입은 무엇이든지 상관없다
// 뒤에 * : 그 안의 모든 클래스의 모든 매서드
// (..) : 매서드 파라미터는 개수/타입 상관없이 모두

// @Before : 대상 메서드 실행 직전에만 실행
// @AfterReturning : 대상 메서드가 정상 반환된 후 실행
// @AfterThrowing : 대상 메서드가 예외를 던졌을 때 실행
// @After : 정상/예외 상관없이 끝나면 항상 실행
// @Around : 대상 메서드 실행을 통째로 감싼다. 전/후/예외 모두 한 매서드에서 제어

// * System.out.println -> @Slf4j 로거로 교체한 이유
// (1) 레벨이 없다 : 전부 같은 급이라, 운영에서 "디버그성 출력만 끄기" 같은 제어가 불가능하다.
// (2) 맥락이 없다 : 시간/스레드/클래스 이름이 자동으로 안 붙는다. (문제 추적이 힘들다)
// (3) 목적지가 고정이다 : 콘솔에만 나간다. 파일 저장, 날짜별 분할(롤링) 같은 걸 못 한다.
// (4) 성능에 불리하다 : 동기 출력이라 요청이 몰리면 병목이 될 수 있다.
// # log.info(...) 로 바꾸면 위 네 가지가 전부 해결된다 - 출력 형태를 보면 차이가 바로 보인다:
//     System.out : [요청 시작] GET /api/boards -> ...
//     log.info   : 2026-07-14T10:00:00.123+09:00  INFO 12345 --- [nio-8080-exec-1] c.e.s.b.aop.LoggingAspect : [요청 시작] ...
//                  └ 시간 ──────────────────────┘ └레벨┘ └PID┘  └── 스레드 ──────┘ └── 어느 클래스가 찍었나 ┘

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* org.example.basicboard.controller..*(..))")
    public void controllerLog() {
        // 매서드 본문은 비워둔다, 실제 로직이 아니라 대상을 가르키는 이름표 역할만 하기 때문
    }

    @Around("controllerLog()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // ProceedingJoinPoint : 지금 가로챈 그 지점에 대한 정보를 담은 객체, 어떤 매서드가 호출됐는지 넘어온 인자는 무엇인지 등을 꺼낼 수 있다.
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        String httpInfo = "";
        // RequestContextHolder : 스프링이 지금 이 요청의 정보를 담아두는 보관소, 어디서든 꺼낼 수 있다.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpInfo = request.getMethod() + "." + request.getRequestURL();
        }

        log.info("[Start Request] {} -> {}", httpInfo, method);
        log.info("[Parameter] {} ", Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed(); // 실행

            long endTime = System.currentTimeMillis(); // 실행 종료 (응답)

            log.info("[End Request] {} : {} ms", method, endTime - startTime);

            return result;
        } catch (Throwable e) {
            long endTime = System.currentTimeMillis();

            log.warn("[Fail Request] {} : {} ms : Exception {}", method, (endTime - startTime), e.getMessage());

            throw e; // 잡은 예외를 다시 던진다, 여기서 예외를 삼켜버리면 컨트롤러가 정상 처리된 것처럼 보여 버그가
        }
    }
}
