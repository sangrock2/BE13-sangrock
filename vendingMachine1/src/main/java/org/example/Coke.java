package org.example;

public class Coke extends Drink {
    public Coke() {
        super("coke", 500, 5);
    }

    @Override
    public void dispense() {
        System.out.println("Dispensed Coke");
    }
}
