package com.fuchsbau.Shorin_world.Game;

public class MovingPoint {
    private double x;
    private double y;
    private double angle;
    private double speed;

    public MovingPoint(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = 0.0;
    }

    public void update(double delta) {
        angle += speed * delta;
        x = Math.cos(angle) * 200 + 450; // Center at x = 450
        y = Math.sin(angle) * 200 + 450; // Center at y = 450
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}