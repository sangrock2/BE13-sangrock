package org.example.singletonadvanced;

public class GreetingServiceGood {
    private static GreetingServiceGood instance = new GreetingServiceGood();

    private GreetingServiceGood() {}

    public static GreetingServiceGood getInstance() {
        return instance;
    }

    public String greet(String name) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {}

        return name;
    }
}
