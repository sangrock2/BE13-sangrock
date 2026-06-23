package org.example.springioc;

public class CoffeeMaker {
    private Bean bean;
    private MilkFrother milkFrother;

    public CoffeeMaker(Bean bean, MilkFrother milkFrother) {
        this.bean = bean;
        this.milkFrother = milkFrother;
    }

    public void brew() {
        System.out.println("bean : " + bean.name() + " brew");
        milkFrother.froth();
    }
}
