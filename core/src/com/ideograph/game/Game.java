package com.ideograph.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	SpriteBatch batch_character; // ideograph_advantage
	Texture img_character;   // img of ideograph_advantage
	SpriteBatch batch_float_de; // float de
	Texture img_float_de;    // float de
	Sprite sprite;

	double acceleration = 2;
	static double x = 0;
	static double delta_x = 0;
	static double y = 0;
	static double delta_y = 0;

	int de_x = 0;
	int de_y = 0;
	int direct_x = 1;
	int direct_y = 1;
	int max_x = 1920-72;
	int max_y = 1080-72;


	@Override
	public void create () {
		batch_character = new SpriteBatch();
		img_character = new Texture("ideograph_advantage(72x).png");
		batch_float_de = new SpriteBatch();
		img_float_de = new Texture("ideograph_advantage(72x).png");
	}

	public double getDelta_x() {
		return delta_x;
	}

	public void friction() {
		delta_x = delta_x * 0.9;
		delta_y = delta_y * 0.9;
		if(delta_x * delta_x < 1){
			delta_x = 0;
		}
		if(delta_y * delta_y < 1){
			delta_y = 0;
		}
	}

	public void float_de() {
		de_x = de_x + direct_x * 7;
		if(de_x > max_x){
			direct_x = -1;
			de_x = max_x * 2 - de_x;
		}
		if(de_x < 0){
			direct_x = 1;
			de_x = de_x * -1;
		}
		de_y = de_y + direct_y * 5;
		if(de_y > max_y){
			direct_y = -1;
			de_y = max_y * 2 - de_y;
		}
		if(de_y < 0){
			direct_y = 1;
			de_y = de_y * -1;
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			delta_x -= acceleration;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			delta_x += acceleration;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			delta_y += acceleration;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			delta_y -= acceleration;
		}
		friction();
		x = x + delta_x;
		y = y + delta_y;

		batch_character.begin();
		batch_character.draw(img_character, (int) x, (int) y);
		batch_character.end();

		float_de();
		batch_float_de.begin();
		batch_float_de.draw(img_character, de_x, de_y);
		batch_float_de.end();
	}

	@Override
	public void dispose () {
		batch_character.dispose();
		img_character.dispose();
	}
}
