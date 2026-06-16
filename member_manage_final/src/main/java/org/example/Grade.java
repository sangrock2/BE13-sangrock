package org.example;

public enum Grade {
    NORMAL("Basic"), VIP("10% Off + Free Shipping");

    private final String benefit;

    Grade(String benefit) {
        this.benefit = benefit;
    }

    public String getBenefit() {
        return benefit;
    }

    public static Grade from(int choice) {
        return switch (choice) {
            case 2 -> VIP;
            default -> NORMAL;
        };
    }
}
