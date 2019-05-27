package api;

import java.io.Serializable;

public class Stats implements Serializable {

    private int health;
    private int damage;

    public Stats(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }


    public int getDamage() {
        return damage;
    }

    public void lowerHealth(int damage) {
        this.health = this.health - damage;
    }

    public void raiseHealth(int healAmt) {
        this.health = this.health + healAmt;
    }

}
