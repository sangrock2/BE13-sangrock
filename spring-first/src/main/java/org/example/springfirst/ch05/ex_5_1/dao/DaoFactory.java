package org.example.springfirst.ch05.ex_5_1.dao;

import org.example.springfirst.ch05.ex_5_1.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public JdbcContext jdbcContext() { return new JdbcContext((connectionMaker())); }

    @Bean
    public UserService userService() { return new UserService(userDAO()); }
}
