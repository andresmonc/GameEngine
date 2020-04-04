package api;

import java.io.Serializable;

public class Stats implements Serializable {

    private int health;
    private int damage;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private static final float GRAVITY = -50;
    private boolean isAirborn = false;



    public int getHealth() {
        return health;
    }


    public int getDamage() {
        return damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void lowerHealth(int damage) {
        this.health = this.health - damage;
    }

    public void raiseHealth(int healAmt) {
        this.health = this.health + healAmt;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public float getCurrentTurnSpeed() {
        return currentTurnSpeed;
    }

    public void setCurrentTurnSpeed(float currentTurnSpeed) {
        this.currentTurnSpeed = currentTurnSpeed;
    }

    public float getUpwardsSpeed() {
        return upwardsSpeed;
    }

    public void setUpwardsSpeed(float upwardsSpeed) {
        this.upwardsSpeed = upwardsSpeed;
    }

    public static float getGRAVITY() {
        return GRAVITY;
    }

    public boolean isAirborn() {
        return isAirborn;
    }

    public void setAirborn(boolean airborn) {
        isAirborn = airborn;
    }
}
