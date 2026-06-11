package org.example;

public class Cider implements Drink {
    private String name = "Cider";
    private int price = 700;
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
