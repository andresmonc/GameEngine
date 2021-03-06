package engineTester;

import NPCEngine.NPCEngine;
import api.Save.AutoSave;
import api.Save.Save;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.NPC.Werewolf;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import worldEditing.ObjectCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldEditorLoop {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        // OpenGL expects vertices to be defined counter clockwise by default


        DisplayManager.createDisplay();
        Loader loader = new Loader();


        //********** TERRAIN TEXTURE STUFF *******************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        //****************************************************


        /* Player */
        TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("person", loader), new ModelTexture(loader.loadTexture("playerTexture")));
        ModelTexture playerTexture = playerModel.getTexture();
        playerTexture.setShineDamper(100);
        playerTexture.setReflectivity(10);

        /* Tree */
        TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));

        /* Low Poly Tree */
        TexturedModel lowPolyTreeModel = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));

        /* Grass */
        //TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        //grassModel.getTexture().setHasTransparency(true);
        //grassModel.getTexture().setUseFakeLighting(true);

        /* Flower */
        //TexturedModel flowerModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flowerTexture")));
        //flowerModel.getTexture().setHasTransparency(true);
        //flowerModel.getTexture().setUseFakeLighting(true);

        /* Fern */
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernTextureAtlases"));
        fernTextureAtlas.setNumberOfRows(2);
        TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        fernModel.getTexture().setHasTransparency(true);



        /* Box */
        TexturedModel boxModel = new TexturedModel(OBJLoader.loadObjModel("box", loader), new ModelTexture(loader.loadTexture("box")));





        /* Terrain */
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");    // darker the spot the lower the spot
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");




        /* Create entities */
        Random random = new Random();
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 400; ++i) {
            if (i % 2 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(x, y, z), 0,
                        random.nextFloat() * 360, 0, 0.9f));

            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(lowPolyTreeModel, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
                x = random.nextFloat() * 800 - 400;
                z = random.nextFloat() * -600;
                y = terrain.getHeightOfTerrain(x, z);
                entities.add(new Entity(treeModel, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
            }
        }

        entities.add(new Entity(boxModel, new Vector3f(100, 5,
                -10), 0, 0, 0, 5f));

        entities.add(new Entity(boxModel, new Vector3f(100, 5,
                -100), 0, 0, 0, 5f));








        /* Light */

        /* Lamp */
        TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));


//		Light sun = new Light(new Vector3f(0, 10000, -7000), new Vector3f(2, 2, 2)); // light source // light color
        List<Light> lights = new ArrayList<>();

        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, -4.2f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -293), 0, 0, 0, 1));


//		lights.add(sun);


        MasterRenderer renderer = new MasterRenderer();


        /* Player */
        Player player = (Player) Save.load();
        if (player == null) {
            player = new Player(playerModel, new Vector3f(100, 0, -50), 0, 180, 0, 0.6f);
        }



        /* Camera */
        Camera camera = new Camera(player);

        /* NPCs */
        NPCEngine npcEngine = new NPCEngine();
        //Werewolf
        Werewolf werewolf = new Werewolf(playerModel, new Vector3f(100, 0, -50), 0, 180, 0, 2.6f);
        Werewolf werewolf2 = new Werewolf(playerModel, new Vector3f(100, 0, -80), 0, 180, 0, 2.6f);
        Werewolf werewolf3 = new Werewolf(playerModel, new Vector3f(100, 0, -10), 0, 180, 0, 2.6f);
        npcEngine.addNpc(werewolf);
        npcEngine.addNpc(werewolf2);
        npcEngine.addNpc(werewolf3);



        /* AutoSaver */
        AutoSave autoSave = new AutoSave();

        /* GUI */
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.7f, -0.85f), new Vector2f(0.20f, 0.25f));
        guis.add(gui);
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
        Vector3f currentPos = picker.getCurrentRay();

        /* Object creator*/
        ObjectCreator objectCreator = new ObjectCreator(loader,picker);

        while (!Display.isCloseRequested()) {
            camera.move();

            player.move(terrain);
            checkInputs(player);
            renderer.processEntity(player);

            // update mouse picker
            picker.update();
//            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
//            if (terrainPoint != null) {
//                werewolf.setPosition(terrainPoint);
//            }

            if (currentPos == null || currentPos.getX() != picker.getCurrentRay().getX() || currentPos.getY() != picker.getCurrentRay().getY() ||
                    currentPos.getZ() != picker.getCurrentRay().getZ()) {
                // temporary
                currentPos = picker.getCurrentRay();
                System.out.println(picker.getCurrentRay());
            }

            // Start ObjectCreator
            objectCreator.start();
            objectCreator.getEntities().forEach(renderer::processEntity);
            objectCreator.getTempEntities().forEach(renderer::processEntity);
//            objectCreator.getEntities().forEach(entity -> AutoSave.save(entity));


            // NPC movement and entity processing
            npcEngine.getNpcList().forEach(npc -> {
                npc.move(terrain);
                npcEngine.beginRandomizedMovement();
                renderer.processEntity(npc);
            });

            autoSave.autoSave(player);


            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            entities.forEach(renderer::processEntity);


            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    // this will handle manual saves for now
    private static void checkInputs(Player player) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Save.save(player);
            System.out.println("Current pos:");
            System.out.println("x:" + player.getPosition().x);
            System.out.println("y:" + player.getPosition().y);
            System.out.println("z:" + player.getPosition().z);
        }
    }


}