package org.example.springfirst.oh01.ex_1_2.dao;

import org.example.springfirst.oh01.ex_1_2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 문제점
// 상속을 통해 DB 커넥션 기능을 확장해서 사용하게 했던 게 다시 불가능해졌다.
// UserDAO 코드가 connectionMaker라는 특정 클래스에 종속되어 있기 때문이다.
// 또 DB 커넥션을 제공하는 클래스가 어떤 것인지를 UserDAO가 구체적으로 알고 있어야 한다.

// 인터페이스의 도입
// 두 개의 클래스가 긴밀하게 연결되지 않게 만듦

// 자바가 추상화를 위해 제공하는 가장 유용한 도구는 바로 인터페이스이다.
// 인터페이스를 통해 접근하게 하면 실제 구현 클래스를 바꿔도 신경 쓸 일이 없다.

public class UserDAO_2 {
    private ConnectionMaker_2 connectionMaker;

    public UserDAO_2() {
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
