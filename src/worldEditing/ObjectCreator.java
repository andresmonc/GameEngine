package worldEditing;

import api.Save.Save;
import entities.Entity;
import entities.Player;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import toolbox.MousePicker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class ObjectCreator {

    private List<Entity> entities = new ArrayList<>();

    private List<Entity> tempEntities = new ArrayList<>();
    private List<Entity> possibleEntities = new ArrayList<>();

    private int index = 0;
    Loader loader;
    MousePicker picker;

    TexturedModel fernModel;
    TexturedModel lowPolyTreeModel;
    Random random = new Random();

    public ObjectCreator(Loader loader, MousePicker picker) {
        this.loader = loader;
        this.picker = picker;
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernTextureAtlases"));
        fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        lowPolyTreeModel = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
        buildPossibleEntities();
    }

    public void start() {

        checkInputs();
    }

    private void checkInputs() {
        if (Mouse.isButtonDown(0) && !tempEntities.isEmpty()) {
            entities.add(tempEntities.get(0));
            tempEntities.clear();
            possibleEntities.clear();
            buildPossibleEntities();
        } else if (Mouse.isButtonDown(1)) {
            createNewEntity();
        }
        if (!tempEntities.isEmpty()) {
            tempEntities.get(0).setPosition(picker.getCurrentTerrainPoint());
        }
    }

    private void createNewEntity() {
        index+=1;
        if (index > possibleEntities.size()-1){
            index = 0;
        }
        if (!tempEntities.isEmpty() && tempEntities.get(0) == possibleEntities.get(index)) {
            tempEntities.set(0,possibleEntities.get(index));
        }
        if (tempEntities.isEmpty()) {
            tempEntities.add(possibleEntities.get(index));
        }

    }

    private void buildPossibleEntities() {
        possibleEntities.add(new Entity(lowPolyTreeModel, new Vector3f(0, 0, 0), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
        possibleEntities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(0, 0, 0), 0,
                random.nextFloat() * 360, 0, 1.9f));
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getTempEntities() {
        return tempEntities;
    }
}


