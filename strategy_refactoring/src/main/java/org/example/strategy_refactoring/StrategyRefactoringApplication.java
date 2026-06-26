package org.example.strategy_refactoring;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StrategyRefactoringApplication {
    public static void main(String[] args) {
        Database database = new Database();
        UserDao userDao = new UserDao(database);

        userDao.deleteAll();
        userDao.add(new User("test123", "kim", "123"));
        userDao.add(new User("test124", "lee", "124"));

        System.out.println("\n====================");

        for (User user: database.getUsers()) {
            System.out.println(user.getName());
        }

    }

}
