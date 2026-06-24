package org.example.springfirst.ch01.ex_1_1.dao;

import org.example.springfirst.ch01.ex_1_1.domain.User;

import java.sql.*;

// UserDao 리팩토링 버전

public class UserDAO_2 {
    public void add(User user) throws ClassNotFoundException, SQLException {
        String quary = "insert into users (id, name, password) values(?,?,?)";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(quary)
        ) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        String quary = "select * from users where id = ?";

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(quary)
        ) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));

            return user;
        }
    }


    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "programmers", "password1234");

        return conn;
    }
}
