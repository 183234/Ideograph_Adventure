package com.ideograph.game;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class MapLoader {
    public static TiledMap map_current;

    public void loadmap(int MapId) {
        if (MapId > 6) {
            MapId = 0;
        }
        String filename = "level/" + MapId + ".tmx";
        TiledMap map;
        if (map_current != null) {
            map_current.dispose();
        }
        map_current = new TmxMapLoader().load(filename);
    }

}