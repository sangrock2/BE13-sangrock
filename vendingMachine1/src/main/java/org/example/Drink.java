package org.example;

public abstract class Drink {
    protected String name;
    protected int price;
    protected int quantity;

    public Drink(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract void dispense();

    public void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

}
