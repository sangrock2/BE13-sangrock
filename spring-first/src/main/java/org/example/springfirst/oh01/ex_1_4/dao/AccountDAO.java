package org.example.springfirst.oh01.ex_1_4.dao;

public class AccountDAO {
    private ConnectionMaker connectionMaker;

    public AccountDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
