package org.example.springfirst.ch03.ex_3_2.dao;


import org.example.springfirst.ch03.ex_3_2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 전략 패턴의 적용
// 컨텍스트 : 변하지 않는 부분 - JDBC 커넥션/실행/자원관리 공통 호출
// 전략 : 변하는 부분 - 어떤 PreparedStatement를 만들지 -> 인터페이스로 추상화

// 컨텍스트는 '인터페이스(StatementStrategy)에만' 의존하고, 실제 전략은 런타임에 주입받는다.
// 그래서 새 기능을 추가해도 컨텍스트 코드는 닫혀 있고(수정X) 전략만 새로 만들면 된다(확장O) = OCP.

public abstract class UserDAO {
    private ConnectionMaker connectionMaker;

    public UserDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    protected UserDAO() {}

    // 컨텍스트 : 변하지 않는 JDBC 작업의 공통 흐름
    //  - 커넥션을 얻고, 전달받은 '전략'에게 statement 생성을 맡기고, 실행하고, 자원을 정리한다.
    //  - 어떤 SQL을 실행할지는 전혀 모른다. 그건 strategy가 결정한다(인터페이스에만 의존).
    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try (
            Connection conn = connectionMaker.getConnection();
            PreparedStatement ps = statementStrategy.makeStatement(conn);
        ) {
            ps.execute();
        }
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        jdbcContextWithStatementStrategy(new UserDaoAdd(user));
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new UserDaoDeleteAll());
    }
}
