package org.example.springfirst.ch03.ex_3_4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private ConnectionMaker connectionMaker;

    public JdbcContext(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try (
                Connection conn = connectionMaker.getConnection();
                PreparedStatement ps = statementStrategy.makeStatement(conn);
        ) {
            ps.execute();
        }
    }
}
