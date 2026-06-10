 package org.example;

 import java.util.Scanner;

 public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hero hero = new Hero("Hero", 100, 25, 0);

        Character[] monsters = {
                new Monster("Slime"),              // 기본 생성자
                new Monster("Goblin", 50, 10, 5),       // 커스텀
                new Dragon("Dragon", 120, 25, 10)      // 보스
        };

        for (Character monster : monsters) {
            System.out.println("\n========== Monster : " + monster.getName()  + " ==========");

            while (hero.isAlive() && monster.isAlive()) {
                System.out.println("\n[Your Turn] Choose action: [1]Attack  [2]Defend  [3]Heal");
                System.out.print("> ");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    hero.attack(monster);
                    monster.showStatus();
                } else if (choice == 2) {
                    hero.defenseMode();
                } else if (choice == 3) {
                    hero.heal();
                } else {
                    System.out.println("Invalid Choice");
                    continue;
                }

                if (!monster.isAlive()) {
                    System.out.println("knocked down the " + monster.getName());
                    break;
                }

                monster.attack(hero);
                hero.showStatus();

                System.out.println("=================================");
            }

            if (!hero.isAlive()) {
                System.out.println("Hero Die");
                break;
            }
        }

        if (hero.isAlive()) {
            System.out.println("Hero is WIN");
        }
    }
}