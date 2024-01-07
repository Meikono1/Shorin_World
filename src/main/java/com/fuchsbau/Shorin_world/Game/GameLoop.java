package com.fuchsbau.Shorin_world.Game;

import com.fuchsbau.Shorin_world.Game.UI.GameWindow;

public class GameLoop implements Runnable {
    private static final int TARGET_FPS = 180;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Time for one frame in nanoseconds
    private static final int FPS_UPDATE_INTERVAL = 1000; // Update FPS every 1000 milliseconds
    private int frameCount;
    private long lastFpsUpdateTime;

    private boolean running = false;
    private GameWindow canvas;

    public GameLoop(GameWindow canvas) {
        this.canvas = canvas;
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
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
            System.nanoTime();
            long waitTime = (OPTIMAL_TIME - renderTime) / 1000000; // Convert to milliseconds


            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastRenderTime = System.nanoTime();

            // Calculate FPS
            frameCount++;
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastFpsUpdateTime >= FPS_UPDATE_INTERVAL) {
                double fps = 1000.0 * frameCount / (currentTimeMillis - lastFpsUpdateTime);
                lastFpsUpdateTime = currentTimeMillis;
                frameCount = 0;
                canvas.setFPS(fps); // Update FPS on the GameCanvas
            }
        }
    }

    private void update(double delta) {
        canvas.update(delta);
        // Update game logic based on delta (elapsed time)
    }

    private void render() {
        // Render game on the canvas
        //canvas.repaint();
    }
}