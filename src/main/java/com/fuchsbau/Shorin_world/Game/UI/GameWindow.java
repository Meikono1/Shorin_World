package com.fuchsbau.Shorin_world.Game.UI;

import com.fuchsbau.Shorin_world.Game.Game;
import com.fuchsbau.Shorin_world.Game.Images.BufferedImages;
import com.fuchsbau.Shorin_world.Game.MovingPoint;
import com.fuchsbau.Shorin_world.Game.Scenes.LevelEditorScene;
import com.fuchsbau.Shorin_world.Game.Scenes.LevelScene;
import com.fuchsbau.Shorin_world.Game.Scenes.Scene;
import com.fuchsbau.Shorin_world.Game.Util.Time;
import com.fuchsbau.Shorin_world.Logger.Filelogger;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameWindow {
    private static final Font FPS_FONT = new Font("Arial", Font.BOLD, 18);
    private static final DecimalFormat FPS_FORMAT = new DecimalFormat("0.##");
    private static GameWindow instance = null;
    private final MovingPoint movingPoint;
    private long glfwWindow;
    public float r, g, b, a;
    private double fps;
    private static Scene currentScene = null;

    public GameWindow(String name, int height, int width) {
        init();
        instance = this;

        movingPoint = new MovingPoint(450, 250, 0.01); // Center at (450, 450) with angular speed of 0.01 radians per frame
        //this.setPreferredSize(new Dimension(width, height));

        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialise GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialise GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create Window
        glfwWindow = glfwCreateWindow(width, height, name, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create GLFW Window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-Sync
        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        changeScene(0);
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }

    private void init() {
        Logger logger = Filelogger.getLogger();
        logger.info("info text");
        logger.severe("severe text");
        r = 1;
        g = 1;
        b = 1;
        a = 1;
        BufferedImages.devider1.setScaledImage(50, 50);
        BufferedImages.devider2.setScaledImage(50, 50);
        BufferedImages.devider3.setScaledImage(50, 50);
        BufferedImages.devider4.setScaledImage(50, 50);
        BufferedImages.devider5.setScaledImage(50, 50);
        BufferedImages.devider6.setScaledImage(50, 50);
        BufferedImages.devider1side.setScaledImage(50, 50);
        BufferedImages.devider2side.setScaledImage(50, 50);
        BufferedImages.devider3side.setScaledImage(50, 50);
        BufferedImages.devider4side.setScaledImage(50, 50);
        BufferedImages.devider5side.setScaledImage(50, 50);
        BufferedImages.devider6side.setScaledImage(50, 50);
        BufferedImages.corner.setScaledImage(375, 375);
        BufferedImages.bottom_ending.setScaledImage(165, 215);
        BufferedImages.top_ending.setScaledImage(215, 165);
        BufferedImages.woodenbackground.setScaledImage(300, 300);
        BufferedImages.stonebackground.setScaledImage(300, 300);
    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float deltaTime = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {

            //Poll Events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);
           // System.out.println("" + (1.0 / deltaTime) + "FPS");

            if (deltaTime >= 0) {
                currentScene.update(deltaTime);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;
        }
        /*


        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        //fill background;
        for (int i = 0; i < canvasWidth / BufferedImages.woodenbackground.getWidth() + 1; i++) {
            for (int j = 0; j < canvasHeight / BufferedImages.woodenbackground.getHeight() + 1; j++) {
                g.drawImage(BufferedImages.woodenbackground.getScaledImage(), i * BufferedImages.woodenbackground.getWidth(), j * BufferedImages.woodenbackground.getHeight(), null);
            }
        }

        for (int i = 0; i < canvasWidth / BufferedImages.stonebackground.getWidth() + 1; i++) {
            for (int j = 0; j < canvasHeight / BufferedImages.stonebackground.getHeight() + 1; j++) {
                g.drawImage(BufferedImages.stonebackground.getScaledImage(), i * BufferedImages.stonebackground.getWidth() + 250, canvasHeight - 450 - (j * BufferedImages.stonebackground.getHeight()), null);
            }
        }


        // Dispalay corner
        g.drawImage(BufferedImages.corner.getScaledImage(), 240, canvasHeight - 525, null);

        // Display bottom images in a row
        int imageCount = (canvasWidth - 500) / BufferedImages.devider1.getWidth(); // Number of images to display
        for (int i = 0; i < imageCount; i++) {
            Image scaledimage = BufferedImages.devider1.getScaledImage();
            switch (i % 6) {
                case 0:
                    break;
                case 1:
                    scaledimage = BufferedImages.devider2.getScaledImage();
                    break;
                case 2:
                    scaledimage = BufferedImages.devider3.getScaledImage();
                    break;
                case 3:
                    scaledimage = BufferedImages.devider4.getScaledImage();
                    break;
                case 4:
                    scaledimage = BufferedImages.devider5.getScaledImage();
                    break;
                case 5:
                    scaledimage = BufferedImages.devider6.getScaledImage();
                    break;
            }

            int x = i * BufferedImages.devider1.getWidth() + 240 + BufferedImages.corner.getWidth();
            int y = canvasHeight - 200;
            g.drawImage(scaledimage, x, y, null);
        }

        // Display side images
        imageCount = (canvasHeight - 500) / BufferedImages.devider1side.getHeight();
        for (int i = 0; i < imageCount; i++) {
            Image scaledimage = BufferedImages.devider1side.getScaledImage();
            switch (i % 6) {
                case 0:
                    break;
                case 1:
                    scaledimage = BufferedImages.devider2side.getScaledImage();
                    break;
                case 2:
                    scaledimage = BufferedImages.devider3side.getScaledImage();
                    break;
                case 3:
                    scaledimage = BufferedImages.devider4side.getScaledImage();
                    break;
                case 4:
                    scaledimage = BufferedImages.devider5side.getScaledImage();
                    break;
                case 5:
                    scaledimage = BufferedImages.devider6side.getScaledImage();
                    break;
            }

            int x = 240;
            int y = canvasHeight - 200 - i * BufferedImages.devider1side.getHeight() - BufferedImages.corner.getHeight();
            g.drawImage(scaledimage, x, y, null);
        }

        g.drawImage(BufferedImages.bottom_ending.getScaledImage(), canvasWidth - BufferedImages.bottom_ending.getWidth(), canvasHeight - 315, null);
        g.drawImage(BufferedImages.top_ending.getScaledImage(), 240, 0, null);

        // Draw FPS on the top right corner
        g.setColor(Color.BLACK);
        g.setFont(FPS_FONT);
        String fpsText = "FPS: " + FPS_FORMAT.format(fps);
        int fpsTextWidth = g.getFontMetrics().stringWidth(fpsText);
        g.drawString(fpsText, getWidth() - fpsTextWidth - 10, 25);
        g.drawString(String.valueOf(canvasWidth), getWidth() - g.getFontMetrics().stringWidth(fpsText) - 10, 55);
        g.drawString(String.valueOf(canvasHeight), getWidth() - g.getFontMetrics().stringWidth(fpsText) - 10, 75);

        g.setColor(GameSettings.getInstance().getColour());
        g.fillOval(movingPoint.getX(), movingPoint.getY(), 50, 50);

         */

    }

    public void update(double delta) {
        movingPoint.update(delta);
    }

    public void setFPS(double fps) {
        this.fps = fps;
    }

    public void freeSpace() {
        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    public static GameWindow getInstance() {
        return instance;
    }
}