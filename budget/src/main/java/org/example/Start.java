package org.example;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBook book = new AccountBookImpl();

        while (true) {
            System.out.println();

            System.out.println("===== BUDGET =====");
            System.out.println("[1]addAccount [2]showAccount [3]deleteAll [4]deleteItem [5]EXIT");
            System.out.println("==================");

            System.out.print("Enter your choice: ");
            int menu = Integer.parseInt(sc.nextLine());

            System.out.println();

            switch (menu) {
                case 1: book.addAccount(); break;
                case 2: book.showAccount(); break;
                case 3: book.deleteAll(); break;
                case 4: book.deleteItem(); break;
                case 5: System.out.println("EXIT"); return;
                default: System.out.println("WRONG NUMBER");
            }
        }
    }
}
