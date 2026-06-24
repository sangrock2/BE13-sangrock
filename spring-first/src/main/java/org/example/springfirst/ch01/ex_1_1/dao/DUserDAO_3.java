package org.example.springfirst.ch01.ex_1_1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDAO_3 extends UserDAO_3 {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "programmers", "password1234");

        return conn;
    }
}
