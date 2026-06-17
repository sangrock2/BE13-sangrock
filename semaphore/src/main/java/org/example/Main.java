package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon(3);
        GoldRanking goldRanking = new GoldRanking();
        String[] names = {"Warrior", "Wizard", "Archer", "Bandit", "Holy Knight"};

        List<Thread> adventurers = new ArrayList<Thread>();

        for (String name : names) {
            Adventurer Adventurer = new Adventurer(dungeon, goldRanking, name);
            adventurers.add(Adventurer);
            Adventurer.start();
        }

        for (Thread adventurer : adventurers) {
            try {
                adventurer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        goldRanking.print();
    }
}