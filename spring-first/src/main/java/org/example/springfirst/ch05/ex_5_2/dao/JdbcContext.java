package org.example.springfirst.ch05.ex_5_2.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 트랜잭션에 참여 할 수 있게 바꿈
// 이전 : 매번 새 커넥션 만들고 try-with-resources로 바로 닫았음
// 메서드마다 커넥션이 다르므로, 여러 DAO 호출을 하나의 트랜잭션으로 묶을 수 없다.
// 지금 : 스프링 Datasource + DataSourceUtils를 쓴다

// DataSourceUtils.getConnection(dataSource)
// 현재 진행 중인 트랜잭션이 있으면 '그 트랜잭션에 묶인 커넥션'을 돌려준다. 커넥션을 메서드 파라미터로 넘기지 않아도 공유된다
// 트랜잭션이 없으면 새 커넥션을 준다.
// ataSourceUtils.releaseConnection(conn, dataSource)
// 트랜잭션에 묶인 커넥션이면 '닫지 않는다'(트랜잭션이 끝날 때 닫는다).트랜잭션 밖이면 닫는다.
// 커넥션을 try-with-resources로 직접 닫으면 트랜잭션이 깨진다. finally에서 release.
// 어떤 일련의 작업이 하나의 트랜잭션으로 묶이려면 그 작업이 진행되는 동안 DB 커넥션도 하나만 사용돼야 한다.

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
