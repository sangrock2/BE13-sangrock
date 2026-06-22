package org.example.springfirst.oh01.ex_1_2.dao;

import org.example.springfirst.oh01.ex_1_2.domain.User;

import java.sql.*;

// 문제점
// 커넥션 객체를 가져오는 방법을 분리하기 위해 상속구조를 만들어버리면, 후에 다른 목적으로 UserDAO에 상속을 적용하기 어렵다
// 상속을 통한 상하위 클래스의 관계는 생각보다 밀접하다

public abstract class UserDAO {
    private ConnectionMaker connectionMaker;

    public UserDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        String quary = "insert into users (id, name, password) values(?,?,?)";

        try (
                Connection conn = connectionMaker.getConnection();
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
                Connection conn = connectionMaker.getConnection();
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
}
