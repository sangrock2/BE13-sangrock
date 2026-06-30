package org.example.springfirst.ch05.ex_5_2.dao;

import org.example.springfirst.ch05.ex_5_2.service.UserService;
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
    public UserService userService() { return new UserService(userDAO(), transactionManager()); }

    // 커넥션을 우리가 직접 만들던 SimpleConnectionMaker 대신, 스프링 표준 DataSource를 쓴다.
    // DriverManagerDataSource: 학습용 DataSource(요청마다 커넥션 생성). 운영은 커넥션 풀을 쓴다.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("programmers");
        dataSource.setPassword("password1234");
        return dataSource;
    }

    // 트랜잭션 추상화의 구현
    // 반환 타입은 추상화 인터페이스(PlatformTransactionManager).
    // JDBC를 쓰므로 DataSourceTransactionManager를 꽂는다.
    // JPA면 JpaTransactionManager, 분산이면 JtaTransactionManager로
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
