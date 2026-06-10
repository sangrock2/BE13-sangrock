package org.example;

public class Hero extends Character{
    public Hero(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    public void defenseMode() {
        int defenseAmount = 5;
        setDefense(getDefense() + defenseAmount);

        System.out.println("[SKILL] Hero increase defense +" + defenseAmount);
    }

    public void heal() {
        int healAmount = 20;
        setHp(getHp() + healAmount);

        System.out.println("[SKILL] Hero Hp healed " + healAmount);

    }
}
