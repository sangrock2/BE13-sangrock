package org.example.singletonbasic;

public class NaiveTicketMachine {
    private int number = 0;

    public int issue() {
        number++;
        return number;
    }
}
