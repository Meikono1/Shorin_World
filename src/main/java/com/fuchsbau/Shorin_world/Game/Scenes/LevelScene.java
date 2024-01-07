package com.fuchsbau.Shorin_world.Game.Scenes;

import com.fuchsbau.Shorin_world.Game.UI.GameWindow;

public class LevelScene extends Scene {
    public LevelScene() {
        System.out.println("LevelScene");
        GameWindow.getInstance().r = 1;
        GameWindow.getInstance().g = 1;
        GameWindow.getInstance().b = 1;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void init() {

    }
}
