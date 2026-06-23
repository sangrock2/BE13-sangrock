package org.example.singletonbasic;

public class TicketMachine {
    private static TicketMachine instance = new TicketMachine();
    private int number = 0;

    private TicketMachine() {}

    public static TicketMachine getInstance() {
        return instance;
    }

    public int issue() {
        number++;
        return number;
    }

    public void reset() {
        number = 0;
    }

    public int peek() {
        return number;
    }
}
