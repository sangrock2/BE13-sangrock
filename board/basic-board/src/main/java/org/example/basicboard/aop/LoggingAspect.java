package org.example.basicboard.aop;

import jakarta.servlet.http.HttpServletRequest;
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

@Aspect
@Component
public class LoggingAspect {
    // 표현식 해석
    // execution : 메서드 실행 지점을 대상으로 한다는 지시
    // 가장 앞 * : 반환 타입은 무엇이든지 상관없다
    // 뒤에 * : 그 안의 모든 클래스의 모든 매서드
    // (..) : 매서드 파라미터는 개수/타입 상관없이 모두
    @Pointcut("execution(* org.example.basicboard.controller..*(..))")
    public void controllerLog() {
        // 매서드 본문은 비워둔다, 실제 로직이 아니라 대상을 가르키는 이름표 역할만 하기 때문
    }

    // @Around : 언제/무엇을 할지 정의하는 어드바이스
    // 어드바이스에는 5가지 종류가 있다
    // @Before : 대상 메서드 실행 직전에만 실행
    // @AfterReturning : 대상 메서드가 정상 반환된 후 실행
    // @AfterThrowing : 대상 메서드가 예외를 던졌을 때 실행
    // @After : 정상/예외 상관없이 끝나면 항상 실행
    // @Around : 대상 메서드 실행을 통째로 감싼다. 전/후/예외 모두 한 매서드에서 제어

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

        System.out.println("[Start Request]" + httpInfo + " ->" + method);
        System.out.println("[Parameter]" + Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();

        try {
            // 실행
            Object result = joinPoint.proceed();

            // 실행 종료(응답)
            long endTime = System.currentTimeMillis();
            System.out.println("[End Request]" + method + " :" + (endTime - startTime) + "ms");

            return result;
        } catch (Throwable e) {
            long endTime = System.currentTimeMillis();
            System.out.println("[Fail Request]" + method + " :" + (endTime - startTime) + "ms" + " : Exception: " + e.getMessage());

            // 잡은 예외를 다시 던진다, 여기서 예외를 삼켜버리면 컨트롤러가 정상 처리된 것처럼 보여 버그가
            throw e;
        }
    }
}
