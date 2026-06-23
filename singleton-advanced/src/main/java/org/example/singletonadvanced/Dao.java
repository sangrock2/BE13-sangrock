package org.example.singletonadvanced;

import java.sql.SQLException;

interface ConnectionMaker {
    String makeConnection() throws SQLException;
}

class SimpleConnectionMaker implements ConnectionMaker {
    private static SimpleConnectionMaker instance = new SimpleConnectionMaker();

    private SimpleConnectionMaker() {}

    public static SimpleConnectionMaker getInstance() {
        return instance;
    }

    @Override
    public String makeConnection() throws SQLException {
        return "connection OK";
    }
}

class UserDao {
    private static UserDao instance = new UserDao();
    private ConnectionMaker connectionMaker = SimpleConnectionMaker.getInstance();

    private UserDao() {}

    public static UserDao getInstance() {
        return instance;
    }

    public String findUser(String userId) throws SQLException {
        return "user id : " + connectionMaker.makeConnection();
    }
}

public class Dao {
}
