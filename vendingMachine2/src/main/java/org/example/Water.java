package org.example;

public class Water implements Drink {
    private String name = "Water";
    private int price = 300;
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
