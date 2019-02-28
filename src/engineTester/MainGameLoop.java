package engineTester;

import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        StaticShader shader = new StaticShader();


        //placeholder!!!
        float[] vertices = {
                -0.5f, 0.5f, 0f, //V0
                -0.5f, -0.5f, 0f, //V1
                0.5f, -0.5f, 0f, //V2
                0.5f, 0.5f, 0f  //V3
        };

        int[] indices = {
                0,1,3,
                3,1,2
        };

        float[] textureCoords = {
                0,0, //V0
                0,1, //V1
                1,1, //V2
                1,0  //V3
        };

        RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("Stone_Wall"));
        TexturedModel staticModel = new TexturedModel(model,texture);

        Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);

        while (!Display.isCloseRequested()) {
            entity.increasePosition(-0.002f,0,0);
            entity.increaseRotation(0,1,0);
            renderer.prepare();
            //start shader
            shader.start();
            //game logic
            renderer.render(entity,shader);
            shader.stop();
            //render
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();

    }
}
