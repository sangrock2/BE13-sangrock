package org.example;

public interface Drink extends Comparable<Drink> {
    String getName();
    int getPrice();
    int getStock();
    void decreaseStock();

    default void dispense() {
        System.out.println("Dispende " + getName());
    }

    @Override
    default int compareTo(Drink o) {
        return Integer.compare(this.getPrice(), o.getPrice());
    };
}
