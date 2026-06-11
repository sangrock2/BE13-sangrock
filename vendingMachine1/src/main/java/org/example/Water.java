package org.example;

public class Water extends Drink {
    public Water() {
        super("water", 300, 5);
    }

    @Override
    public void dispense() {
        System.out.println("Dispensing water");
    }
}
