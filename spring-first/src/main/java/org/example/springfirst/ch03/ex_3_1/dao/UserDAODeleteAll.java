package org.example.springfirst.ch03.ex_3_1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAODeleteAll extends UserDAO {
    @Override
    protected PreparedStatement makeStatement(Connection conn) throws SQLException, ClassNotFoundException {
        return conn.prepareStatement("DELETE FROM user");
    }
}
