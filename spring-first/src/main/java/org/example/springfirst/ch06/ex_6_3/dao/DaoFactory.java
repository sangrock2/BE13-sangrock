package org.example.springfirst.ch06.ex_6_3.dao;

import org.example.springfirst.ch06.ex_6_3.service.TransactionAdvice;
import org.example.springfirst.ch06.ex_6_3.service.UserService;
import org.example.springfirst.ch06.ex_6_3.service.UserServiceImpl;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
    public UserService userService() {
        return new UserServiceImpl(userDAO());
    }

    // 자동 프록시 생성기
    // 빈 후 처리기다. 컨테이너가 빈을 만드는 도중에 끼어들어 가공한다
    // 등록된 모든 advisor의 porintcut을 검사해서, 조건에 맞는 빈을 자동으로 프록시로 바꾼다.
    //  => 더 이상 빈마다 ProxyFactoryBean을 일일이 설정하지 않아도 된다(ex_6_2의 반복이 사라짐).
    //     target 빈은 평범하게 등록만 해두면, 이 생성기가 알아서 프록시를 입혀준다.
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("upgrade*");

        return pointcut;
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDAO());
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
}
