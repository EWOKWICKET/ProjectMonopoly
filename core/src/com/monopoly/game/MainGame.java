package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGame {
    private  Texture texture;
    private Skin mySkin;

    SpriteBatch batch;
    Stage stage1;
    Stage stage2;

    private OrthographicCamera camera1;
    private OrthographicCamera camera2;
    private Viewport viewport1;
    private Viewport viewport2;

    public MainGame(Stage stage, SpriteBatch batch) {
        this.stage1=stage;
        this.batch=batch;

        batch.flush();  // Flushes the batch
        stage.clear();  // Removes all actors from the stage

        // Create cameras
//        camera1 = new OrthographicCamera();
//        camera2 = new OrthographicCamera();
//
//        // Create viewports
//        viewport1 = new ScreenViewport(camera1);
//        viewport2 = new ScreenViewport(camera2);
//
//        // Set positions and sizes of the viewports
//        viewport1.setScreenBounds(0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
//        viewport2.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);

      //  stage1 = new Stage(viewport1);
     //   stage2=  new Stage(viewport2);
//        Gdx.input.setInputProcessor(stage1);
//        Gdx.input.setInputProcessor(stage1);


        mySkin=new Skin(Gdx.files.internal("gdx-skins-master/default/skin/uiskin.json"));

        Label label = new Label("AAAAA", mySkin);
        label.setPosition(100,100);
        stage1.addActor(label);
        Label label2 = new Label("AA234567AAA", mySkin);
        label2.setPosition(50,100);
       // stage2.addActor(label2);

        texture = new Texture("monopolyField.jpg");



        Image image = new Image(texture);
        stage1.addActor(image);
      //  image.setScaling(Scaling.stretchX);

    }


    public void update() {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Set camera matrices and update viewports
//        viewport1.apply();
//        camera1.update();
//        batch.setProjectionMatrix(camera1.combined);
//
//        // Draw first half of the screen
//        // Your drawing code here
//
//        viewport2.apply();
//        camera2.update();
//       batch.setProjectionMatrix(camera2.combined);

        // Draw second half of the screen
        // Your drawing code here

//        batch.begin();
//      batch.end();
//        stage1.act();
//        stage1.draw();
//        stage2.act();
//        stage2.draw();

    }


}
