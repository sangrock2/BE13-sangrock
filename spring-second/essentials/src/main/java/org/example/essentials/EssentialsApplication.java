package org.example.essentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 웹 서버, WAS, 톰캣이란?

// 웹 서버 : 브라우저의 요청을 받아 이미 만들어져 있는 정적인 파일(HTML, CSS, 이미지 등)을 그대로 전달해주는 서
// 대표적인 웹 서버 : 아파치, Nginx
// 장점 : 정적 파일 전달, HTTPS 처리, 요청 분배(로드 밸런싱) 같은 일은 잘함
// 단점 : 자바 코드를 직접 실행해 결과를 만들어 내지는 못함

// WAS(Web Application Server)
// 사용자가 브라우저로 요청을 보내면, 요청을 받아 코드를 실행하고 그 결과를 응답으로 반환한다
// 웹 서버와 달리 동적인 처리를 요청마다 프로그램을 돌려 만든다
// 요청/응답 처리, 스레드 관리, 세션 관리 같은 서버 공통 기능을 대신 맡아 준다
// 보통은 웹 서버를 앞단에 두어 정적 요소를 처리하고 동적 요청만 뒷단의 WAS로 넘기는 구조를 함께 쓰기도 한다.

// 톰캣 : WAS중에 가장 널리 쓰이는 오픈소스 구현체
// 자바의 서블릿 규격을 따르며, 스프링 MWC도 결국 이 서블릿 위에서 동작한다
// 톰캣은 정적 파일도 어느 정도 다룰 수 있어, 규모가 작으면 웹 서버 없이 톰캣만으로도 서비스가 가능하다
// 톰캣을 따로 설치하고 그 안에 애플리케이션을 넣어 실행하는것이 일반적이다
// 스프링부트는 톰캣을 라이브러리 형태로 내장되어있다. 그래서 설치 없이 main 실행만으로 서버가 함께 떠서 요청을 받을 수 있다

// HTTP((HyperText Transfer Protocol)
// 브라우저(클라이언트)와 서버가 서로 데이터를 주고 받을 때 지키는 약속이다.
// 통신을 항상 요청과 응답 한 쌍으로 이루어진다
// 요청 : 무엇을(URL), 어떻게(메서드), 부가 정보(헤더), 본문(Body)가 담긴다
// 응답 : 상태코드(200 성공, 404 없음, 500 서버 오류 등)와 실제 데이터가 담긴다
// HTTP는 무상태 프로토콜이라 각 요청을 서로를 기억하지 못함 그래서 로그인 상태 유지를 위에 세션, 쿠키 등이 필요하다

// * HTTP 메서드 - GET, POST, PUT, DELETE
// 메서드는 '이 요청으로 무엇을 하고 싶은지'를 나타내는 동사다.
// 데이터의 생성/조회/수정/삭제(CRUD)와 자연스럽게 짝지어진다.
// - GET : 데이터를 '조회'할 때 쓴다.
// 서버의 상태를 바꾸지 않으며, 값은 주로 URL뒤 쿼리 스트링에 담는다.
// - POST : 데이터를 '새로 생성'할 때 쓴다.
// 보낼 내용을 요청 본문(body)에 담으며, 서버의 상태를 바꾼다.
// - PUT : 기존 데이터를 '수정(전체 교체)'할 때 쓴다.
// 같은 요청을 여러 번 보내도 결과가 같은 '멱등성'을 가진다.
// - DELETE : 데이터를 '삭제'할 때 쓴다.
// - 스프링에서는 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 으로
// 각 메서드 요청을 컨트롤러의 특정 메서드에 연결한다.

// * 멱등성
// 멱등성은 '같은 요청을 한 번 보내든 여러 번 보내든, 서버의 최종 상태가 똑같이 유지되는 성질'을 말한다.
// '여러 번 눌러도 결과가 그대로'다
// - GET : 멱등하다. 여러 번 조회해도 데이터가 바뀌지 않는다.
// - PUT : 멱등하다. 예를들어 회원 3번의 이름을 '홍길동'으로 바꿔라 를 10번 보내도, 결과는 언제나 '이름이 홍길동'인 하나의 상태다.
// - DELETE : 멱등하다. 예를들어 회원 3번을 삭제하라 -> 여러 번 보내도 3번이 없는 상태는 동일하다.
// - POST : 멱등하지 '않다'. "회원을 새로 등록하라" 10번 보내면 10명 생겨 버린다. 보낼 때마다 상태가 계속 달라진다.
// 네트워크 오류로 응답을 못 받아 요청을 '재시도'하는 상황이 생겼을 때, 이때 멱등한 요청은 여러 번 가도 안전하지만,
// 멱등하지 않은 POST는 중복 처리(예, 결제 두 번)가 될 수 있어 주의해야 한다.

// 스프링부트 애플리케이션
// @SpringBootApplication : @SpringConfiguration, @ComponentScan, @EnableAutoConfiguration 을 합쳐 놓은 것
// @SpringConfiguration : 이 클래스가 설정 클래스임을 알리며, 내부의 Bean 정의를 스프링 컨테이너에 등록
// @ComponentScan : 하위 패키지를 훑으며 @Component, @Service, @Repository, @Controller 등이 붙은 Bean 을 찾아 등록한다.
// @EnableAutoConfiguration : 자동 구성을 켜는 핵심 스위치

// 실행 매커니즘
// 1. SpringApplication.run()이 호출되면 가장 먼저 Appilication-스프링 컨테이너를 생성한다
// 2. 웹 관련 클래스가 클래스패스에 있는지 확인해 웹 애플리케이션 타입을 스스로 판단한다
// 3. @ComponentScan이 우리가 직접 작성한 번들을 먼저 스캔에 등록한다
// 4. 그 다음 @EnableAutoConfiguration이 자동 구성 호보들을 불러온다

// 자동 구성의 순서와 원리
// 1. 스프링부트는 META-INF/spring/....AutoConfiguration.imports 파일에 나열된 수많은 자동 구성 후보 클래스 목록을 읽어 들인다.
// 2. 각 후보는 @ConditionalOnClass, @ConditionalOnMissingBean, @ConditionalOnProperty 같은 '조건(Condition)'을 달고 있다.
// 이 조건이 충족될 때만 해당 구성이 실제로 적용된다. 예를 들어 클래스패스에 톰캣이 있으면 내장 톰캣이 자동으로 구성되는 식이다.
// 3. 사용자 정의 빈이 우선, @ConditionalOnMissingBean 덕분에 개발자가 직접 만든 빈이 있으면 자동 구성은 물러나고,
// 없을 때만 기본값을 채워 넣는다. 그래서 자동 구성은 항상 사용자 설정보다 나중에, 그리고 조건부로 동작한다.
// 4. 구성 간 순서가 필요한 경우 @AutoConfiguration(before/after), @AutoConfigureOrder 등으로 상대적 순서를 조정한다.

// [Spring 과 Spring Boot 의 차이]
// 스프링은 DI, IoC 컨테이너, AOP 같은 기능을 제공하는 프레임워크다. 강력하지만 프로젝트를 시작할 때 개발자가 직접 챙겨야 할 것이 많다.
// - 라이브러리 버전을 하나하나 맞춰야 한다.
// - XML 이나 JavaConfig 로 DispatcherServlet, ViewResolver 등 설정을 직접 작성해야 한다.
// - 톰캣 같은 웹 서버(WAS)를 따로 설치하고 WAR 로 배포해야 한다.

// 스프링부트(Spring Boot)는 이런 스프링을 더 쉽게 쓰도록 감싼 도구다.
// 스프링을 대체하는 것이 아니라, 스프링 위에 편의 기능을 얹은 것이다.
// - starter 의존성으로 호환되는 라이브러리 버전을 한 번에 맞춰 준다.
// - 위에서 설명한 자동 구성(Auto Configuration)으로 반복적인 설정을 대신 해 준다.
// - 내장 톰캣을 품고 있어 별도 WAS 설치 없이 실행 가능한 JAR 로 바로 띄울 수 있다.
// - Actuator 같은 운영/모니터링 기능도 기본 제공한다.

// 한마디로 스프링이 '엔진'이라면, 스프링부트는 그 엔진을 얹어 바로 달릴 수 있게
// 조립해 둔 '완성차'에 가깝다. 그래서 설정보다 비즈니스 로직에 집중할 수 있다.

// [정리]
// 결국 스프링부트는 "컴포넌트 스캔으로 내 빈을 먼저 등록하고,
// 부족한 부분은 조건에 맞는 자동 구성으로 채운 뒤,
// 웹 서버를 띄우고 컨테이너를 완성한다"는 흐름으로 동작한다.

@SpringBootApplication
public class EssentialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EssentialsApplication.class, args);
    }

}
