package org.example.springfirst.ch06.ex_6_2.dao;

import org.example.springfirst.ch06.ex_6_2.service.TransactionAdvice;
import org.example.springfirst.ch06.ex_6_2.service.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactoryBean;
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

    // UserService Bean = ProxyFactoryBean이 생산하는 프록시
    //  - target과 advisor만 등록하면, 스프링이 프록시를 알아서 만들어준다.
    //  - 여러 advisor를 addAdvisor로 얹을 수도 있다(부가기능 여러 개 조합).
    @Bean
    public ProxyFactoryBean userService() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userServiceImpl());
        proxyFactoryBean.addAdvisor(transactionAdvisor());

        return proxyFactoryBean;
    }

    // Advisor : Pointcut + Advice
    // 부가기능과 적용대상을 묶은 한 덩어리. ProxyFactoryBean에는 이 Advisor 단위로 등록한다.
    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    // Advice(무엇을) : 적용할 부가기능 자체
    @Bean
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(transactionManager());
    }

    // Pointcut(어디에) : 어떤 메서드에 부가기능을 적용할지
    //  - ex_6_1에서는 핸들러 안에서 startsWith로 직접 걸렀지만, 이제 그 책임이 Pointcut으로 분리됐다.
    //  - NameMatchMethodPointcut: 메서드 이름 패턴으로 매칭. "upgrade*" -> upgradeLevels 등.
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
