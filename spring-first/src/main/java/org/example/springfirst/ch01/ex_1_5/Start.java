package org.example.springfirst.ch01.ex_1_5;


import org.example.springfirst.ch01.ex_1_2.domain.User;
import org.example.springfirst.ch01.ex_1_5.dao.DaoFactory;
import org.example.springfirst.ch01.ex_1_5.dao.UserDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

// 스프링의 제어의 역전(IoC)
// 스프링의 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트를 빈(Bean)이라고 한다.
// 컨테이너가 생성과 관계설정, 사용 등을 제어해주는 제어의 역전이 적용된 오브젝트
// ApplicationContext : IoC 방식을 따라 만들어진 일종의 빈 팩토리

// 컴포넌트
// 애플리케이션을 구성하는, 독립적으로 같이 끼울 수 있는 부품 같은 오브젝트
// 컴포넌트의 핵심은 비즈니스 로직에만 집중하고, 자신이 어떻게 만들어지는지, 누구와 연결되는지는 신경 쓰지 않는다"는 점이다.
// 이렇게 만들어진 컴포넌트는 코드 수정 없이 다른 컴포넌트로 교체하거나 재사용할 수 있다.

// 컨테이너
// 컴포너트들을 담아두고, 생성하고, 연결해주는 생성주기까지 관리해주는 실행 환경을 말한다.

// 오브젝트 팩토리로 직접 사용했을 때와 비교해서 애플리케이션 컨텍스트를 사용했을 때 얻을 수 있는 장점
// 클라이언트는 구체적인 팩토리 클래스를 알 필요가 없다 / 필요한 오브젝트를 가져오려면 어떤 팩토리 클래스를 사용해야 할지 알아야 하고, 필요할 때마다 팩토리 오브젝트를 생성해야 하는 번거로움이 있다.
// 애플리케이션 컨텍스트는 종합 IoC 서비스를 제공해준다.
// 관계설정, 오브젝트가 만들어지는 방식, 자동생성, 오브젝트에 대한 후처리, 정보의 조합, 설정 방식의 다변화, 인터셉터 등 다양한 기능을 제공한다.
// 또, 빈이 사용할 수 있는 기반기술 서비스나 외부 시스템과의 연동 등을 컨테이너 차원에서 제공해준다.
// 애플리케이션 컨텍스트는 빈을 검색하는 다양한 방법을 제공한다

public class Start {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 1) 스프링 컨테이너(애플리케이션 컨텍스트)를 만든다.
        // AnnotationConfigApplicationContext : @Configuration 자바 클래스를 설정정보로 읽는 컨텍스트 구현체
        // 생성자에 넘긴 DaoFactory.class 가 바로 "어떤 빈을 어떻게 만들지" 알려주는 설정정보다.
        // 이 줄이 실행되는 순간 컨테이너는 DaoFactory의 @Bean 메서드들을 호출해
        // UserDAO, ConnectionMaker 오브젝트를 미리 만들어 관계까지 맺어 담아둔다.
        // (즉, 객체 생성·연결의 제어권이 우리 코드가 아니라 컨테이너로 넘어갔다 = 제어의 역전)
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        // 2) 컨테이너에서 필요한 빈을 꺼내 쓴다(우리가 직접 new 하지 않는다).
        // getBean("userDao", UserDAO.class)
        // "userDao"     : 가져올 빈의 이름. 기본적으로 @Bean 메서드 이름(userDao)이 빈 이름이 된다.
        // UserDAO.class : 돌려받을 타입. 형변환 없이 바로 UserDAO로 받기 위해 타입을 함께 지정.
        // 이미 connectionMaker가 주입되어 완성된 UserDAO 오브젝트를 컨테이너가 돌려준다.
        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);

        User user = userDAO.get("test1");
        System.out.println(user.getName());


    }

}
