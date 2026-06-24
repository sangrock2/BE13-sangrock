package org.example.springfirst.ch03.ex_3_2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll implements StatementStrategy {
    @Override
    public PreparedStatement makeStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("DELETE FROM user");
    }
}
