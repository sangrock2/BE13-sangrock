package org.example.singletonadvanced;

public class GreetingServiceBad {
    private static GreetingServiceBad instance = new GreetingServiceBad();
    private String name;

    private GreetingServiceBad() {}

    public static GreetingServiceBad getInstance() {
        return instance;
    }

    public String greet(String name) {
        this.name = name;

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return this.name;
    }
}
