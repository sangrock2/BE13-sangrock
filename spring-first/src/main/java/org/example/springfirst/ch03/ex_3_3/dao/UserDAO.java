package org.example.springfirst.ch03.ex_3_3.dao;


import org.example.springfirst.ch03.ex_3_3.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// 익명 클래스

public abstract class UserDAO {
    private ConnectionMaker connectionMaker;

    public UserDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    protected UserDAO() {}

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try (
            Connection conn = connectionMaker.getConnection();
            PreparedStatement ps = statementStrategy.makeStatement(conn);
        ) {
            ps.execute();
        }
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        class UserDaoAdd implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("insert into users(id, name, password) values(?,?,?)");

                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        }

        jdbcContextWithStatementStrategy(new UserDaoAdd());
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        class UserDaoDeleteAll implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("DELETE FROM user");
            }
        }

        jdbcContextWithStatementStrategy(new UserDaoDeleteAll());
    }
}
