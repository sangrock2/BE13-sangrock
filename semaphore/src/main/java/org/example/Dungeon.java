package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Dungeon {
    private final Semaphore slots;
    private final int capacity;

    public static volatile boolean bossEvent = false;

    public Dungeon(int capacity) {
        this.slots = new Semaphore(capacity, true);
        this.capacity = capacity;
    }

    public static void isBossEvent() throws InterruptedException {
        while (bossEvent) {
            Thread.sleep(50);
        }
    }

    public int enter(String name) throws InterruptedException {
        isBossEvent();
        System.out.println(name + " Waiting Dungeon ... ");

        boolean acquired = slots.tryAcquire(5, TimeUnit.SECONDS);

        if (!acquired) {
            isBossEvent();
            System.out.println(name + " Failed to acquire Dungeon ... ");
            return -1;
        }

        try {
            isBossEvent();
            System.out.println("[Enter] " + name + " (Remain Slot: " + slots.availablePermits() + "/" + capacity + ")");

            int gold;
            boolean isBoss = Math.random() < 0.05;

            if (isBoss) {
                bossEvent = true;
                System.out.println("\n================================\n\n\n");
                System.out.println("[BOSS] " + name + "is Fight the Boss");
                System.out.println("\n\n\n================================");

                Thread.sleep(1000);
                bossEvent = false;

                Thread.sleep((int)(Math.random() * 6000) + 2000);
                gold = (int)(Math.random() * 1200) + 200;
            } else {
                Thread.sleep((int)(Math.random() * 2000) + 1000);
                isBossEvent();
                gold = (int)(Math.random() * 400) + 100;
            }

            isBossEvent();
            System.out.println("[Clear] " + name + " +" + gold + " Gold");

            return gold;
        } finally {
            isBossEvent();
            System.out.println("[Exit] " + name);
            slots.release();
        }
    }
}
