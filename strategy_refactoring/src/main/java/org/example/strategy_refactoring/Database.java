package org.example.strategy_refactoring;

import java.util.ArrayList;
import java.util.List;

class Database {
    private final List<User> users = new ArrayList<>();

    void open()  { System.out.println("  [Context] Open Connection"); }
    void close() { System.out.println("  [Context] Close Connection"); }

    List<User> getUsers() { return users; }
}