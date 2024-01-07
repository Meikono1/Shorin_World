package com.fuchsbau.Shorin_world.Game.Scenes;


import com.fuchsbau.Shorin_world.Game.UI.Camera;
import com.fuchsbau.Shorin_world.Game.UI.MouseListener;
import com.fuchsbau.Shorin_world.Game.renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    private boolean changingScene = false;
    private float timetochangescene = 2.0f;
    private int vertexID, fragmentID, shaderProgram;

    private Shader defaultShader;

    private float[] vertexArray = {
            //position                  //color
            50.5f, -50.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,  //Bottom Right  0
            -50.5f, 50.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,  //Top left      1
            50.5f, 50.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,   //Top Right     2
            -50.5f, -50.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, //Bottom left   3
    };


    //COUNTER-CLOCKWISE
    private int[] elementArray = {
            2, 1, 0,
            0, 1, 3
    };


    private int vaoID, vboID, eboID;

    public LevelEditorScene() {
        System.out.println("LevelEditorScene");
        defaultShader = new Shader("resources/shaders/default.glsl");
        defaultShader.compile();
    }

    @Override
    public void update(float deltaTime) {
        if(MouseListener.isDragging()){
            MouseListener.getInstance().updateCamera(this.camera, deltaTime);
        }


        defaultShader.use();
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());

        //Bind the VAO
        glBindVertexArray(vaoID);

        //Enable the Vertex
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());


        //Generate VAO, VBO and EBO Buffer
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO and upload vertex Buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indicies and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorsSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorsSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorsSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);

    }
}
