package org.example;

import java.util.Scanner;

// 객체지향, 추상클래스
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        VendingMachine machine = new VendingMachine();

        while (true) {
            machine.printMenu();
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice >= 1 && choice <= 5){
                machine.buy(choice);
            } else if (choice == 6){
                System.out.print("Insert Money : ");
                machine.insertMoney(Integer.parseInt(sc.nextLine()));
                System.out.println();
            } else if (choice == 7){
                System.out.println("return Money : " + machine.returnChange());
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}