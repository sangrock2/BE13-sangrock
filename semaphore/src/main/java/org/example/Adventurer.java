package org.example;

public class Adventurer extends Thread {
    private static final int REPEAT = 5;

    private Dungeon dungeon;
    private GoldRanking goldRanking;
    private String name;

    public Adventurer(Dungeon dungeon, GoldRanking ranking, String name) {
        this.dungeon = dungeon;
        this.goldRanking = ranking;
        this.name = name;
    }

    public void run() {
        try {
            for (int i = 0; i < REPEAT; i++) {
                Dungeon.isBossEvent();

                int gold = dungeon.enter(name);

                if (gold != -1) {
                    goldRanking.put(name, gold);
                }

                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
