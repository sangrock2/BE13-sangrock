package org.example;

public class VendingMachine {
    private int totalMoney;
    private Drink[] drinks;

    public VendingMachine() {
        totalMoney = 0;
        drinks = new Drink[] {
                new Coke(), new Cider(), new Fanta(), new Water(), new Coffee()
        };
    }

    public void insertMoney(int money) {
        totalMoney += money;
        System.out.println("Inserted money: " + money);
    }

    public void printMenu() {
        System.out.println("================================= VENDING MACHINE ================================");
        System.out.println("[1]COKE-500KRW [2]CIDER-700KRW [3]FANTA-900KRW [4]WATER-300KRW [5]COFFEE-1200KRW [6]INSERT [7]EXIT");
        System.out.println("now money : " + totalMoney + " KRW");
        System.out.println("==========================================================================");
    }

    public void buy(int menuNumber) {
        Drink drink = drinks[menuNumber - 1];

        if (drink.getQuantity() <= 0) {
            System.out.println("Out of stock.");
        }

        if (totalMoney < drink.getPrice()) {
            System.out.println("You don't have enough money");
            return;
        }

        totalMoney -= drink.getPrice();
        drink.decreaseQuantity();
        drink.dispense();
    }

    public int returnChange() {
        int change = totalMoney;

        int coin500 = change / 500;
        int coin100 = (change % 500) / 100;

        System.out.println("500 KRW coins : " + coin500);
        System.out.println("100 KRW coins : " + coin100);

        totalMoney = 0;
        return change;
    }
}
