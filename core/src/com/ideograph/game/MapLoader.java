package com.ideograph.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class MapLoader {
    TiledMap current_map = new TiledMap();
    public static TiledMap map_current;

    public void loadmap(int MapId) {
        String filename = "level/" + MapId + ".tmx";
        TiledMap map;
        if (map_current != null) {
            map_current.dispose();
        }
        map_current = new TmxMapLoader().load(filename);
    }

}