package com.ideograph.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class level_tutorial extends ApplicationAdapter {
    //tiled map stuff
    TiledMap tiledmap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        tiledmap = new TmxMapLoader().load("tutorial.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledmap);
    }


    public void new_render() {
        //camera.update();
        tiledMapRenderer.setView(camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //camera.translate((float) Game.delta_x, (float) Game.delta_y);
        //camera.update();
        //new_render();
        tiledMapRenderer.render();
    }
}
