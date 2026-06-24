package org.example.springfirst.ch01.ex_1_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

// UserDAO 입장에서 어떤 클래스로 만들어졌는지 상관없이 ConnectionMaker_2 를 호출하면 Connection 타입의 오브젝트를 기대할 수 있다.

public interface ConnectionMaker_2 {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}
