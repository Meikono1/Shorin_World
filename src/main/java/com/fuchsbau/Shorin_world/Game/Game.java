package com.fuchsbau.Shorin_world.Game;

import com.fuchsbau.Shorin_world.Settings.GameSettings;
import com.fuchsbau.Shorin_world.Game.UI.GameWindow;


public class Game {

    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Time for one frame in nanoseconds
    private boolean running = false;
    private GameWindow game;


    public Game(String name, int height, int width) {
        GameSettings.getInstance();
        game = new GameWindow(name, height, width);
        game.loop();

        //Free the Memory
        game.freeSpace();


    }

    private void init() {

    }

    public void run() {
        init();
        long lastUpdateTime = System.nanoTime();
        long lastRenderTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;
            double delta = elapsedTime / ((double) OPTIMAL_TIME);

            // Update game logic
            update(delta);

            // Render game
            render();

            // Delay to maintain the desired frame rate
            long renderTime = System.nanoTime() - lastRenderTime;
            long waitTime = (OPTIMAL_TIME - renderTime) / 1000000; // Convert to milliseconds
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastRenderTime = System.nanoTime();
        }
    }

    private void update(double delta) {
        // Update game logic based on delta (elapsed time)
    }

    private void render() {
        // Render game on the canvas
        //game.repaint();
    }
}
