package org.example.springfirst.oh01.ex_1_1.dao;

import org.example.springfirst.oh01.ex_1_1.domain.User;

import java.sql.*;

// 문제점
// 서로 다른 종류의 DB를 사용하고 싶을때 더 나아가 이후에도 DB 커넥션을 가져오는 방법을 변경할 수 있어야함
// 소스코드를 제공하지 않아도 컴파일된 클래스 바이러니 파일만 제공해도 DB 커넥션 변경을 가능하게

// 상속을 통한 확장
// 클래스 계층구조를 통해 두 개의 관심이 독립적으로 분리되면서 변경 작업을 한층 용이하게 함

// * 디자인 패턴
// 소프트웨어 설계 시 특정 상황에서 자주 만나는 문제를 해결하기 위해 사용할 수 있는 재사용 가능한 솔루션을 말한다.

// * 템플릿 메서드 패턴
// 변하지 않는 기능은 슈퍼클래스에 만들어두고 자주 변경되며 확장할 기능은 서브클래스에서 만들도록 한다.
// 슈퍼클래스에서 디폴트 기능을 정의해두거나 비워뒀다가
// 서브클래스에서 선택적으로 오버라이드할 수 있도록 만들어둔 메서드를 훅(hook) 메서드라고 한다.

public abstract class UserDAO_3 {
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

    // UserDAO의 소스코드를 제공하면, geteConnection() 메서드를 원하는 방식으로 확장한 후 UserDAO 기능과 함께 사용이 가능하다
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
