package org.example.essentials.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 디스패치 서블릿
// 스프링 MVC로 들어오는 모든 http 요청은 먼저 DispatcherServlet 하나를 거친다
// 그래서 이 서블릿을 프론트 컨트롤러라고 부른다
// 개발자가 만들지 않아도 스프링부트가 자동으로 미리 등록해준다

// 요청 하나가 들어와 응답이 나가기까지의 호출은 다음고 ㅏ같다
// 1. 브라우저 요청이 필터를 지나 DispatcherServlet에 도착한다
// 2. HandlerMapping 에게 이 URL을 처리할 컨트롤러가 누구지하고 묻는다
// 3. HandlerAdapter 를 통해 실제 컨트롤러 메서드를 호출한다 이때 파라미터를 알맞게 만들어 넣어 준다
// 4. 컨트롤러가 값을 반환하면, 그 반환값을 어떻게 처리할지 갈린다.
// - @Controller + 뷰 이름   -> ViewResolver 가 templates/이름.html 을 찾는다.
// - @RestController/@ResponseBody -> HttpMessageConverter 가 데이터(문자열/JSON)로 변환한다.
// 5. 최종 결과를 응답으로 만들어 브라우저에 돌려준다.

// 핵심은, 우리가 @GetMapping 메서드만 작성하면 그 앞뒤의 '분배와 변환'은
// DispatcherServlet 이 정해진 순서대로 대신 처리해 준다는 점이다.
// 아래의 @Controller / @RestController 차이도, 결국 4번 단계에서
// DispatcherServlet 이 반환값을 뷰로 볼지 데이터로 볼지를 가르는 이야기다.

// @Controller와 @RestController의 차이
// 둘 다 탭 요청을 받아 처리하는 컨트롤러지만, 메서드가 반환하는 String을 어떻게 해석하느냐가 다르다
// @Controller : 반환하는 String을 뷰의 이름으로 본다. 그래서 SessionController, CookieController 처럼 HTML 페이지를 보여 줄 떄 사용
// @RestController : 반환하는 String 또는 객체를 데이터 그 자체로 본다. 즉 JSON으로 변환해준다
// @RestController 는 @Controller + @ResponseBody 를 합친 것이다.
// @Controller 에서도 메서드에 @ResponseBody 를 붙이면 데이터를 그대로 반환할 수 있다.
// 즉 @RestController 는 "이 클래스의 모든 메서드는 데이터를 반환한다"는 선언인 셈이다.

//   [하는 일]
//   - 모든 요청에 공통으로 필요한 처리를 컨트롤러보다 먼저 해치운다.
//     예: 인증/인가 검사, 요청 로깅, 문자 인코딩(UTF-8) 설정, CORS 처리 등.
//   - 문지기라서, 통과시키지 않고 여기서 바로 응답을 돌려보내며 막을 수도 있다.
//     (예: 로그인 안 된 요청을 컨트롤러까지 보내지 않고 필터에서 차단한다.)

//   [만드는 법]
//   - jakarta.servlet.Filter 를 구현하고 doFilter() 안에 로직을 작성한다.
//   - doFilter() 안에서 chain.doFilter(request, response) 를 호출해야
//     '다음 단계(다음 필터 또는 DispatcherServlet)'로 요청이 넘어간다.
//     이 호출을 하지 않으면 요청은 여기서 멈춘다.

@RestController
public class FilterController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/api/data")
    public String data() {
        return "data";
    }
}
