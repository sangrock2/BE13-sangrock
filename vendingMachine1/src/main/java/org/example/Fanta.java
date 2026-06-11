package org.example;

public class Fanta extends Drink {
    public Fanta() {
        super("fanta", 900, 5);
    }

    @Override
    public void dispense() {
        System.out.println("dispensed Fanta");
    }
}
