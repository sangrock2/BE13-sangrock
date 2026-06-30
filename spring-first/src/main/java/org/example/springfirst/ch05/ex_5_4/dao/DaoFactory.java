package org.example.springfirst.ch05.ex_5_4.dao;

import org.example.springfirst.ch05.ex_5_4.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public JdbcContext jdbcContext() { return new JdbcContext((dataSource())); }

    @Bean
    public UserService userService() { return new UserServiceTx(transactionManager(), userServiceImpl()); }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDAO(), mailSender());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("programmers");
        dataSource.setPassword("password1234");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    // * 메일 발송 구현을 여기서 결정한다(추상화의 교체 지점).
    //  - 지금은 실제 SMTP 서버가 없으므로 DummyMailSender(아무것도 안 함)를 꽂는다.
    //  - 운영에서는 JavaMail 기반 실제 발송 구현으로 '이 한 줄만' 바꾸면 된다.
    //    UserServiceImpl 코드는 전혀 손대지 않는다.
    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }
}
