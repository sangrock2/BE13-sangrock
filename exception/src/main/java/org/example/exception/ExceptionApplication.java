package org.example.exception;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class ExceptionApplication {
    public static void main(String[] args) {
        FileLogger fileLogger = new FileLogger();
        DataService dataService = new DataService(fileLogger);
        FlakyService flakyService = new FlakyService(3);

        System.out.println("\n========== 1. fetchWithRetry  ==========");

        try {
            String result = dataService.fetchWithRetry(flakyService);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n========== 2. avoidByRethrow  ==========");

        try {
            dataService.avoidByRethrow(flakyService);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("\n========== 3. registerUser  ==========");

        try {
            dataService.registerUser("test");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
