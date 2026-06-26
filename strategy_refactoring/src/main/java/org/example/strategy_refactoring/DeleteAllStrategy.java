package org.example.strategy_refactoring;

public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public void run(Database db) {
        db.getUsers().clear();
        System.out.println("Deleted all users");
    }
}
