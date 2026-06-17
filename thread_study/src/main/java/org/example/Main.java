package org.example;

class PrintDash extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.println("-");
        }
    }
}

class PrintBar extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.println("|");
        }
    }
}

class SleepThread extends Thread {
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.println("-");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("END");
    }
}

class CountThread extends Thread {
    public void run() {
        int i = 10;

        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            for (long x = 0; x < 2_500_000_000L; x++) ;
        }
        System.out.println("Count Done");
    }
}

class CountSleepThread extends Thread {
    public void run() {
        int i = 10;
        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Wake Up! (InterruptedException)");
                break;
            }
        }
        System.out.println("Count Done");
    }
}

class YieldThread extends Thread {
    private String name;
    public YieldThread(String name) { this.name = name; }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " Running : " + i);
            Thread.yield();
            try { Thread.sleep(500); } catch (InterruptedException e) { break; }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        PrintDash t1 = new PrintDash();
        PrintBar t2 = new PrintBar();

        t1.start();
        t2.start();

        long start = System.currentTimeMillis();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Time taken : " + (System.currentTimeMillis() - start) + "ms");
    }
}