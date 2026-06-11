package org.example;

public class Cider extends Drink {
    public Cider() {
        super("cider", 700, 5);
    }

    @Override
    public void dispense() {
        System.out.println("Dispense Cider");
    }
}
