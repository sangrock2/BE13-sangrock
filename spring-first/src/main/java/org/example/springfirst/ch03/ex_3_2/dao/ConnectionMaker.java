package org.example.springfirst.ch03.ex_3_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
    Connection getConnection() throws ClassNotFoundException, SQLException;
}
