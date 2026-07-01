package org.example.springfirst.ch06.ex_6_2.service;

import org.example.springfirst.ch06.ex_6_2.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

public class UserServiceTx implements UserService {
    private PlatformTransactionManager transactionManager;
    private UserServiceImpl userServiceImpl;

    public UserServiceTx(PlatformTransactionManager transactionManager, UserServiceImpl userServiceImpl) {
        this.transactionManager = transactionManager;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void add(User user) throws SQLException, ClassNotFoundException {
        userServiceImpl.add(user);
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            userServiceImpl.upgradeLevels();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
