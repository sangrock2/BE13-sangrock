package org.example.springfirst.ch03.ex_3_1.dao;

import org.example.springfirst.ch02.ex_2_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 템플릿 메서드 패턴의 적용
// 상속을 통해 기능을 확장해서 사용하는 부분이다.
// 변하지 않는 부분은 슈퍼클래스에 두고 변하는 부분을 추상 메서드로 정의해둬서

public abstract class UserDAO {
    private ConnectionMaker connectionMaker;

    public UserDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    protected UserDAO() {}

    public void add(User user) throws ClassNotFoundException, SQLException {
        String quary = "insert into users (id, name, password) values(?,?,?)";

        try (
                Connection conn = connectionMaker.getConnection();
                PreparedStatement ps = conn.prepareStatement(quary);
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

    public void deleteAll() throws SQLException, ClassNotFoundException {
        try (
                Connection conn = connectionMaker.getConnection();
                PreparedStatement ps = makeStatement(conn);
        ) {
            ps.executeUpdate();
        }
    }

    protected abstract PreparedStatement makeStatement(Connection conn) throws SQLException, ClassNotFoundException;
}
