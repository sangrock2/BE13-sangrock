package org.example.strategy_refactoring;

public class AddStrategy implements StatementStrategy{
    private User user;

    public AddStrategy(User user) {
        this.user = user;
    }

    @Override
    public void run(Database db) {
        db.getUsers().add(user);
        System.out.println(user.getName());
    }
}
