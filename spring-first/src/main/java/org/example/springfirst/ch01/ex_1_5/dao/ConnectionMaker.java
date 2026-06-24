package org.example.springfirst.ch01.ex_1_5.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}
