package org.example;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBookImpl book = new AccountBookImpl();

        while (true) {
            System.out.println();
            System.out.println("================= BUDGET (File) =================");
            System.out.println("[1]addAccount [2]showAccount [3]deleteAll [4]EXIT");
            System.out.println("=================================================");

            int menu;

            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    menu = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a NUMBER.");
                    System.out.println();
                }
            }

            System.out.println();

            switch (menu) {
                case 1: book.addAccount(); break;
                case 2: book.showAccount(); break;
                case 3: book.deleteAccount(); break;
                case 4: System.out.println("EXIT"); return;
                default: System.out.println("WRONG NUMBER");
            }
        }
    }
}
