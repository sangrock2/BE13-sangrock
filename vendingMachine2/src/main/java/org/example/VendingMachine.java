package org.example;

import java.util.Arrays;

public class VendingMachine {
    private int totalMoney;
    private Drink[] drinks;

    public VendingMachine() {
        totalMoney = 0;
        drinks = new Drink[] {
                new Coke(), new Cider(), new Fanta(), new Water(), new Coffee()
        };

        Arrays.sort(drinks);
    }

    public void insertMoney(int money) {
        totalMoney += money;
        System.out.println("Inserted money: " + money);
    }

    public void printMenu() {
        System.out.println("================================= VENDING MACHINE ================================");

        for (int i = 0; i < drinks.length; i++) {
            System.out.printf("[%d]%s-%d KRW(%d left)  ", i + 1, drinks[i].getName().toUpperCase(), drinks[i].getPrice(), drinks[i].getStock());
        }

        System.out.println("\n[6]INSERT [7]EXIT");
        System.out.println("now money : " + totalMoney + " KRW");
        System.out.println("==========================================================================");
    }

    public void buy(int menuNumber) {
        Drink drink = drinks[menuNumber - 1];

        if (drink.getStock() <= 0) {
            System.out.println("Out of stock");
        }

        if (totalMoney < drink.getPrice()) {
            System.out.println("You don't have enough money");
            return;
        }
        totalMoney -= drink.getPrice();
        drink.decreaseStock();
        drink.dispense();
    }

    public int returnChange() {
        int change = totalMoney;
        totalMoney = 0;
        return change;
    }

}
