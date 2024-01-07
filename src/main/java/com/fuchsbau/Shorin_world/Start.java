package com.fuchsbau.Shorin_world;

import com.fuchsbau.Shorin_world.Game.Game;

public class Start extends Game{
    public Start(String name, int height, int width) {
        super(name, height, width);
    }

    public static void main(String[] args) {
        new Game("Shorin World", 1080, 1920).run();

    }
}
