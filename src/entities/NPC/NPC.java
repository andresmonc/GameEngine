package entities.NPC;

import entities.Entity;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class NPC extends Entity implements Movable  {
    public NPC(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        test();
    }

    public void test() {
        System.out.println(getPosition().x);
        System.out.println(getPosition().y);
        System.out.println(getPosition().z);
    }

    public void moveForward(){
        setCurrentSpeed(10);
    }

    public void turnLeft(){
        setCurrentTurnSpeed(-10);
    }

    public void turnRight(){
        setCurrentSpeed(10);
    }

    public void move(Terrain terrain) {
        super.increaseRotation(0, getCurrentTurnSpeed() * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = getCurrentSpeed() * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        setUpwardsSpeed(getUpwardsSpeed() + (getGRAVITY() * DisplayManager.getFrameTimeSeconds()));
        super.increasePosition(0, getUpwardsSpeed() * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            setUpwardsSpeed(0);
            super.getPosition().y = terrainHeight;
            setAirborn(false);
        }
        if (getPosition().x < 0) {
            setUpwardsSpeed(0);
            getPosition().x = 0;
        }
        if (getPosition().x > 800) {
            setUpwardsSpeed(0);
            getPosition().x = 800;
        }

        if (getPosition().z > 0) {
            setUpwardsSpeed(0);
            getPosition().z = 0;
        }
        if (getPosition().z < -800) {
            setUpwardsSpeed(0);
            getPosition().z = -800;
        }


    }
}