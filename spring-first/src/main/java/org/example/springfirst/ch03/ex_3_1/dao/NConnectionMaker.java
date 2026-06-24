package org.example.springfirst.ch03.ex_3_1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "programmers", "password1234");

        return conn;
    }
}
