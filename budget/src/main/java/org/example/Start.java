package org.example;

import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBook book = new AccountBookImpl();

        while (true) {
            System.out.println();

            System.out.println("=========================================== BUDGET ===========================================");
            System.out.println("[1]addAccount [2]showAccount [3]deleteAll [4]deleteItem [5]EXIT [6]showTotal [7]updateAccount");
            System.out.println("==============================================================================================");

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
                case 3: book.deleteAll(); break;
                case 4: book.deleteItem(); break;
                case 5: System.out.println("EXIT"); return;
                case 6: book.showTotal(); break;
                case 7: book.updateAccount(); break;
                default: System.out.println("WRONG NUMBER");
            }
        }
    }
}
