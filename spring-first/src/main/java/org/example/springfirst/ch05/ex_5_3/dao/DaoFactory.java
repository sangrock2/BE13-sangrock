package org.example.springfirst.ch05.ex_5_3.dao;

import org.example.springfirst.ch05.ex_5_3.service.UserService;
import org.example.springfirst.ch05.ex_5_3.service.UserServiceImpl;
import org.example.springfirst.ch05.ex_5_3.service.UserServiceTx;
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
