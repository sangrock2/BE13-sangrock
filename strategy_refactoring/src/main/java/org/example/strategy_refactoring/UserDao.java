package org.example.strategy_refactoring;

public class UserDao {
    private Database database;

    public UserDao(Database database) {
        this.database = database;
    }

    public void context(StatementStrategy strategy) {
        database.open();
        strategy.run(database);
        database.close();
    }

    public void deleteAll() {
        context(database -> {
            database.getUsers().clear();
            System.out.println("Deleted all users");
        });
    }

    public void add(User user) {
        context(database -> {
            database.getUsers().add(user);
            System.out.println(user.getName());
        });
    }

    /*
    public void deleteAll() {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().clear();
                System.out.println("Deleted all users");
            }
        };

        context(strategy);
    }

    public void add(User user) {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().add(user);
                System.out.println(user.getName());
            }
        };

        context(strategy);
    }
     */

    /*
    public void deleteAll() {
        context(new DeleteAllStrategy());
    }

    public void add(User user) {
        context(new AddStrategy(user));
    }
     */
}
