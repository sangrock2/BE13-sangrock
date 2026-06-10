package org.example;

public class Pet {
    private String name;
    private int fullness;
    private int happiness;
    private String type;

    public Pet(String name, String type) {
        this.name = name;
        this.type = type;
        this.fullness = 50;
        this.happiness = 50;
    }

    public void showStatus() {
        System.out.println("[" + name + "] fullness: " + fullness + " / happiness: " + happiness);
    }

    public void feed() {
        fullness += 20;
        if (fullness > 100) fullness = 100;
        happiness += 5;
        if (happiness > 100) happiness = 100;
        System.out.println("I gave food to " + name);
    }

    public void play() {
        happiness += 20;
        if (happiness > 100) happiness = 100;
        fullness -= 10;
        if (fullness < 0) fullness = 0;
        System.out.println("I had play with" + name);
    }

    public void sleep(){
        happiness += 10;
        if (happiness > 100) happiness = 100;
        fullness += 10;
        if (fullness > 100) fullness = 100;
    }

    public void showFeel() {
        if (happiness >= 70) {
            System.out.println(name + " is happy\n");
        } else if (30 <= happiness && happiness < 70) {
            System.out.println(name + " is soso\n");
        } else {
            System.out.println(name + " is sad\n");
        }
    }

    public void act() {
        fullness -= 5;
        if (fullness < 0) fullness = 0;
    }

    public String getName() {
        return name;
    }
}
