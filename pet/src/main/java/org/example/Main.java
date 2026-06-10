package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many pets do you want to raise?: ");
        int petCount = Integer.parseInt(sc.nextLine());
        Pet[] pets = new Pet[petCount];

        for (int i = 0; i < pets.length; i++) {
            System.out.println("\n[Pet #" + (i + 1) + " Info]");

            System.out.print("Enter your pet name: ");
            String name = sc.nextLine();

            System.out.print("Enter your pet type: ");
            String type = sc.nextLine();

            pets[i] = new Pet(name, type);
            pets[i].showStatus();
        }

        while (true) {
            System.out.println("\nWhat should I do? [1]Feed [2]Play [3]Sleep [4]showStatus [5]EXIT");
            System.out.print("> ");
            int menu = Integer.parseInt(sc.nextLine());

            System.out.print("Witch pet Number? : > ");
            int petNumber = Integer.parseInt(sc.nextLine()) - 1;
            System.out.println();

            if (menu == 1) {
                pets[petNumber].feed();
                pets[petNumber].showStatus();
            }  else if (menu == 2) {
                pets[petNumber].play();
                pets[petNumber].showStatus();
            }  else if (menu == 3) {
                pets[petNumber].sleep();
            }  else if (menu == 4) {
                pets[petNumber].showStatus();
            } else if (menu == 5) {
                System.out.println("hello!");
                break;
            } else { System.out.println("Please choose between 1 and 4"); }

            for (Pet pet : pets) {
                pet.act();
                pet.showStatus();
                pet.showFeel();
            }
        }
    }
}