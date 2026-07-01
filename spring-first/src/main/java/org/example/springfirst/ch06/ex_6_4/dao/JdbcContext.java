package org.example.springfirst.ch06.ex_6_4.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcContext {
    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try (
            PreparedStatement ps = statementStrategy.makeStatement(conn);
        ) {
            ps.execute();
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource); // 트랜잭션 커넥션이면 닫지 않음
        }
    }

    public <T> List<T> query(StatementStrategy statementStrategy, RowMapper<T> RowMapper) throws SQLException, ClassNotFoundException {
        Connection conn = DataSourceUtils.getConnection(dataSource);

        try (
            PreparedStatement ps = statementStrategy.makeStatement(conn);
            ResultSet rs = ps.executeQuery();
        ) {
            List<T> results = new ArrayList<>();

            while (rs.next()) {
                results.add(RowMapper.mapRow(rs));
            }

            return results;
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    public <T> T queryForObject(StatementStrategy statementStrategy, RowMapper<T> RowMapper) throws SQLException, ClassNotFoundException {
        List<T> results = query(statementStrategy, RowMapper);

        if (results.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return results.getFirst();
    }

}
