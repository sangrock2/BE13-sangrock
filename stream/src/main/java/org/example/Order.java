package org.example;

import java.util.List;

public class Order {
    private int id;
    private List<String> items;

    public Order(int id, List<String> items) {
        this.id = id;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public List<String> getItems() {
        return items;
    }
}
