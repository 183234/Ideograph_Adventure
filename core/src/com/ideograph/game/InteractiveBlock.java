package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class InteractiveBlock {

    public void Interact() {
        int cellId = Game.tiledLayer.getCell((int) Game.character_x / 72, (int) (Game.character_y / 72)).getTile().getId();
        if (cellId == 2) {
            //rollreward();
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
