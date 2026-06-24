package org.example.springfirst.ch01.ex_1_6.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// DaoFactory를 스프링 빈 팩토리가 사용할 수 있는 설정정보로 리팩토링

@Configuration
public class DaoFactory {

    @Bean
    public UserDAO userDAO() {
        return UserDAO.getInstance(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}
