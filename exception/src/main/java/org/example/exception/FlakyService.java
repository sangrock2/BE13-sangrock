package org.example.exception;

public class FlakyService {
    private final int failTimes;
    private int callCount = 0;

    FlakyService(int failTimes) { this.failTimes = failTimes; }

    String fetch() throws java.sql.SQLException {
        callCount++;
        if (callCount <= failTimes)
            throw new java.sql.SQLException("Connection Error (Call " + callCount + ")");
        return "Data-OK";
    }
}
