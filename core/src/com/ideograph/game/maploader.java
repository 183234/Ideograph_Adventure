package com.ideograph.game;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class maploader {
    TiledMap current_map = new TiledMap();
    TiledMap map_current;

    public void loadmap(int MapId) {
        String filename = MapId + ".tmx";
        TiledMap map;
        if (map_current != null) {
            map_current.dispose();
        }
        map_current = new TmxMapLoader().load(filename);
    }

}