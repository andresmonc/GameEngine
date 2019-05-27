package entities;

import java.io.Serializable;

public class Monster implements Serializable {
    // need to extend entity
    private String name;
    private int health;
    private int strength;
    private int goldDrop;

    public Monster(String name, int health, int strength) {
        super();
        this.name = name;
        this.health = health;
        this.strength = strength;
    }


}
