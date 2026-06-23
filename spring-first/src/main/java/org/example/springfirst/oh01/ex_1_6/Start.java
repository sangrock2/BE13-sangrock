package org.example.springfirst.oh01.ex_1_6;

import org.example.springfirst.oh01.ex_1_6.dao.DaoFactory;
import org.example.springfirst.oh01.ex_1_6.dao.UserDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 싱글톤
// 어플리케이션 컨텍스트는 싱글톤을 저장하고 관리하는 싱글톤 레지스트리이기도 하다
// 별다른 설정을 하지 않으면 내부적으로 빈 오브젝트를 모두 싱글톤으로 만든다

// 스프링은 엔터프라이즈 시스템을 위해 고안된 기술이기 때문에 서버 환경에서 사용될 때 그 가치가 있다.
// 요청이 올 때마다 오브젝트를 새로 만들어서 사용한다고 생각하면, 서버가 감당하기 힘들다.

// 서블릿은 대부분 멀티스레드 환경에서 싱글톤으로 동작한다.
// 서블릿 클래스당 하나의 오브젝트만 만들어두고, 사용자의 요청을 담당하는 여러 스레드에서 하나의 오브젝트를 동시에 사용한다.

// 스프링 빈의 스코프
// 스프링이 관리하는 오브젝트, 빈이 생성되고, 존재하고, 적용되는 범위
// 스프링 빈의 기본 스코프는 싱글톤이다.
// 싱글톤 스코프는 컨테이너 내에 한 개의 오브젝트만 만들어져서, 강제로 제거하지 않는 한 스프링 컨테이너가 존재하는 동안 계속 유지된다.

//
//  ┌────────────────────────┬─────────────────────────────────────────┐
//  │          구분           │              무엇이 정하나                │
//  ├────────────────────────┼─────────────────────────────────────────┤
//  │ 빈이 새로 만들어지는가     │ 스코프가 결정 (request면 요청마다 새로)      │
//  ├────────────────────────┼─────────────────────────────────────────┤
//  │ 요청이 대기하는가         │ WAS의 스레드 풀 크기가 결정                 │
//  └────────────────────────┴─────────────────────────────────────────┘
//
//  - WAS(톰캣 등)는 스레드 풀을 둔다 (예: 기본 200개).
//  - 요청 200개까지는 스레드를 배정받아 동시에 처리 → 각자 요청 스코프 빈 생성.
//  - 201번째부터는 스레드가 빌 때까지 큐에서 대기 → 스레드를 받는 순간 그때 자기 빈이 생성된다.

//  - 싱글톤: 클래스당 1개를 모두가 공유 → 그래서 상태(필드)를 가지면 안 됨(무상태 원칙).
//  - 요청 스코프: 요청마다 독립된 빈 → 그 요청 동안의 상태를 안전하게 담아도 됨 (다른 요청과 안 섞임).

public class Start {
    public static void main(String[] args) {
        DaoFactory factory = new DaoFactory();
        UserDAO userDAO01 = factory.userDAO();
        UserDAO userDAO02 = factory.userDAO();

        System.out.println(userDAO01);
        System.out.println(userDAO02);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDAO userDAO03 = context.getBean(UserDAO.class);
        UserDAO userDAO04 = context.getBean(UserDAO.class);

        System.out.println(userDAO03);
        System.out.println(userDAO04);


    }

}
