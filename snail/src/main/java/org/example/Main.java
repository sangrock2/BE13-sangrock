package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many snails should we race? > ");
        int count = Integer.parseInt(sc.nextLine());
        System.out.println();

        int betNum;
        String myPick;

        while (true) {
            try {
                System.out.print("Which snail do you think will win? (1~" + count + ") > ");
                betNum = Integer.parseInt(sc.nextLine());
                myPick = "Snail" + betNum;
                break;
            } catch (Exception e) {
                System.out.println("Please enter a number between 1 and " + (count + 1));
            }
        }

        Snail[] snails = new Snail[count];
        Race race = new Race();

        for (int i = 0; i < count; i++) {
            snails[i] = new Snail("Snail" + (i+1), race);
            snails[i].start();
        }

        while (true) {
            boolean finished = true;

            for (int i = 0; i < 50; i++) {
                System.out.println();
            }

            System.out.println("==========================================");
            for (Snail snail : snails) {
                StringBuilder bar = new StringBuilder();
                for (int i = 0; i < snail.getPosition(); i++) bar.append("=");
                bar.append(">");
                System.out.println(snail.getName() + ": " + bar);

                if (snail.isAlive()){
                    finished = false;
                }
            }
            System.out.println("==========================================\n\n");

            if (finished) break;

            try {
                Thread.sleep(300);
            } catch (InterruptedException _) {}
        }

        try {
            for (Snail s : snails) {
                s.join();
            }
        } catch (InterruptedException _) {}

        List<String> rank = race.getRankList();

        System.out.println("[RANK LIST]");
        for (int i = 0; i < rank.size(); i++) {
            System.out.printf("Rank %d : %s\n", i+1, rank.get(i));
        }
        System.out.println();

        System.out.println("[BET RESULT]");
        if (rank.getFirst().equals(myPick)) {
            System.out.println("Bet result : Success");
        } else {
            System.out.println("Bet result : Failure");
        }
    }
}