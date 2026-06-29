package org.example.exception;

import java.sql.SQLException;

public class DataService {
    private FileLogger fileLogger;

    public DataService(FileLogger fileLogger) {
        this.fileLogger = fileLogger;
    }

    public String fetchWithRetry(FlakyService flaky) {
        int maxRetry = 3;

        for (int i = 0; i < maxRetry; i++) {
            try {
                String rs = flaky.fetch();
                fileLogger.log("INFO", "Success : " + rs);
                return rs;
            } catch (SQLException e) {
                fileLogger.log("WARN", "Fail " + i + ": " + e.getMessage());
            }
        }

        fileLogger.log("ERROR", "Fail Completely");
        throw new RuntimeException("Fail Completely");
    }

    public void avoidByThrows(FlakyService f) throws SQLException {
        f.fetch();
    }

    public void avoidByRethrow(FlakyService f) throws SQLException {
        try {
            f.fetch();
        } catch (SQLException e) {
            fileLogger.log("WARN", "Avoid " + e.getMessage());
            throw e;
        }
    }

    public void registerUser(String id) {
        try {
            insertUser(id);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                fileLogger.log("ERROR", "Id Duplicated");
                throw new DuplicateUserIdException(id, e);
            }

            fileLogger.log("ERROR", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void insertUser(String id) throws SQLException {
        throw new SQLException("TEST", "23000");
    }
}

class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String id, Throwable cause) {
        super(id, cause);
    }
}
