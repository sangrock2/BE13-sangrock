package org.example;

public class Dragon extends Monster {
    public Dragon(String name, int maxHp, int power, int defense) {
        super(name, maxHp, power, defense);
    }

    @Override
    public void attack(Character target) {
        System.out.println(getName() + " attack breathes devastating FIRE ");
        super.attack(target);
    }
}
