package com.fuchsbau.Shorin_world.Game.UI;

import com.fuchsbau.Shorin_world.Settings.GameSettings;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.lastX = 0;
        this.lastY = 0;

    }

    public static MouseListener getInstance() {
        if (instance == null) {
            instance = new MouseListener();
        }

        return instance;
    }

    public static void updateCamera(Camera camera, float deltaTime) {
        camera.position.x += deltaTime * GameSettings.getInstance().scrollspeed * getDx();
        camera.position.y -= deltaTime * GameSettings.getInstance().scrollspeed * getDy();
        normalise();
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        getInstance().lastY = getInstance().yPos;
        getInstance().lastX = getInstance().xPos;
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
        getInstance().isDragging = checkDragging();
    }

    private static void normalise() {
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
    }

    private static boolean checkDragging() {
        for (boolean buttonPressed : getInstance().mouseButtonPressed) {
            if (buttonPressed) {
                return true;
            }
        }
        return false;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < getInstance().mouseButtonPressed.length) {
                getInstance().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < getInstance().mouseButtonPressed.length) {
                getInstance().mouseButtonPressed[button] = false;
                getInstance().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance().scrollX = xOffset;
        getInstance().scrollY = yOffset;
    }

    public static void endFrame() {
        getInstance().scrollX = 0;
        getInstance().scrollY = 0;
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
    }

    public static float getX() {
        return (float) getInstance().xPos;
    }

    public static float getY() {
        return (float) getInstance().yPos;
    }

    public static float getDx() {
        return (float) (getInstance().lastX - getInstance().xPos);
    }

    public static float getDy() {
        return (float) (getInstance().lastY - getInstance().yPos);
    }

    public static float getScrollX() {
        return (float) getInstance().scrollX;
    }

    public static float getScrollY() {
        return (float) getInstance().scrollY;
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button > getInstance().mouseButtonPressed.length) {
            return getInstance().mouseButtonPressed[button];
        } else {
            return false;
        }
    }


}
