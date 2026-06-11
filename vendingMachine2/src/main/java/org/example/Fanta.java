package org.example;

public class Fanta implements Drink {
    private String name = "Fanta";
    private int price = 900;
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
