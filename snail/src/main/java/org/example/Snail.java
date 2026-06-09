package org.example;

import java.util.Random;

public class Snail extends Thread {
    private String name;
    private int position = 0;
    private final int FINISH = 30;
    private Random rand = new Random();
    private Race race;

    public Snail(String name, Race race) {
        this.name = name;
        this.race = race;
    }

    public int getPosition() {
        return position;
    }

    public void run() {
        while (position < FINISH) {
            position += rand.nextInt(3) + 1;

            if (position > FINISH) {
                position = FINISH;
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }

        race.finish(name);
    }
}

