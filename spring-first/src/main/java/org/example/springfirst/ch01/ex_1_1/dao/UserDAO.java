package org.example.springfirst.ch01.ex_1_1.dao;

// DB를 사용해 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 오브젝트
// 분리와 확장을 고려한 설계의 유무가 중요하다

import org.example.springfirst.ch01.ex_1_1.domain.User;

import java.sql.*;

public class UserDAO {
    public void add(User user) throws ClassNotFoundException, SQLException {

        // JDBC 드라이버를 메모리에 로딩하는 코드
        // com.mysql.cj.jdbc.Driver : MySql 드라이버 클래스의 전체 이름
        // 이 클래스가 로딩될 때 내부적으로 DriverManager에 자기 자신을 자동 등록한다.
        // → 등록이 되어야 아래 DriverManager.getConnection(...)으로 DB에 연결할 수 있다.
        // (참고: JDBC 4.0+ 부터는 자동 등록되어 생략 가능하지만, 동작 원리 이해를 위해 작성한다.)
        Class.forName("com.mysql.cj.jdbc.Driver");

        String quary = "insert into users (id, name, password) values(?,?,?)";

        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "programmers", "password1234");
            PreparedStatement ps = conn.prepareStatement(quary)
        ) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String quary = "select * from users where id = ?";

        try (
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory", "programmers", "password1234");
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
