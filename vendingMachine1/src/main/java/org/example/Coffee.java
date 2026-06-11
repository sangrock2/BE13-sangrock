package org.example;

public class Coffee extends Drink {
    public Coffee() {
        super("coffee", 1200, 5);
    }

    @Override
    public void dispense() {
        System.out.println("dispensed Coffee");
    }
}
