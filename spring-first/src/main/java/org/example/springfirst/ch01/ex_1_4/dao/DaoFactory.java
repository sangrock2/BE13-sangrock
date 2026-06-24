package org.example.springfirst.ch01.ex_1_4.dao;

public class DaoFactory {
    public UserDAO userDAO() {
        return new UserDAO(connectionMaker());
    }

    public AccountDAO accountDAO() {
        return new AccountDAO(connectionMaker());
    }

    public MessageDAO messageDAO() {
        return new MessageDAO(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new NConnectionMaker();
    }
}
