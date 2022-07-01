package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class InteractiveBlock {

    public static void rollreward() {
        double Food_rng = Math.random() * 100;
        double Item_rng = Math.random() * 100;

        for (int i = 0; i <= (int) Food_rng / 34; i++) {
            Inventory.addFood((int) (Math.random() * 12), (int) (Math.random() * 3) + 1);
        }

    }

    public static void Interact(TiledMapTileLayer.Cell cell) {
        int cellId = cell.getTile().getId();
        if (cellId == 3) {
            rollreward();
            System.out.println("interacted with chest");
            //set to interacted
        } else if (cellId == 4) {
            //load map
            //send state of getting into next level or smth if needed
            if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                Game.next_level = true;
            }
        }

    }
}
