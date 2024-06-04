package com.monopoly.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MonoPoly extends ApplicationAdapter {
	SpriteBatch batch;
	Stage stage;
	static MainMenu mm;
	OrthographicCamera camera;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
		mm=new MainMenu(stage, batch);
	}

	@Override
	public void render () {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		camera.update();

		batch.begin();
		mm.update();
		batch.end();
		stage.act();
		stage.draw();

	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		batch.dispose();
		mm.dispose();
		stage.dispose();
	}
}