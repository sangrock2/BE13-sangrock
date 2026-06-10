package org.example;

public class Character {
    private String name;
    private int maxHp;
    private int hp;
    private int power;
    private int defense;

    public Character(String name) {
        this(name, 30, 5, 5);
    }

    public Character(String name, int hp, int power, int defense) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.power = power;
        this.defense = defense;
    }

    public void showStatus() {
        System.out.println(name + " (HP: " + hp + ")\n");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void attack(Character target) {
        int dmg = power - target.getDefense();

        if (dmg < 0) {
            dmg = 0;
        }

        System.out.println(name + " attack! " + dmg + " damage to " + target.getName());
        target.takeDamage(dmg);
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public int getDefense() {
        return defense;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
