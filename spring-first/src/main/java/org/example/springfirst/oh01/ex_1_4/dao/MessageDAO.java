package org.example.springfirst.oh01.ex_1_4.dao;

public class MessageDAO {
    private ConnectionMaker connectionMaker;

    public MessageDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
