package org.example;

public class Coffee implements Drink {
    private String name = "Coffee";
    private int price = 1200;
    private int stock = 5;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void decreaseStock() {
        if (stock > 0) {
            stock--;
        }
    }
}
