package com.fuchsbau.Shorin_world.Game.Scenes;

import com.fuchsbau.Shorin_world.Game.UI.Camera;

public abstract class Scene {

    protected Camera camera;

    public Scene() {

    }

    public abstract void update (float deltaTime);


    public abstract void init();
}
