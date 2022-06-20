package com.ideograph.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.*;

public class movement {
    float x = Game.character_x;
    float y = Game.character_y;
    float gravity = 2.0f;
    float CoR = 0.8f; //coefficient of Restitution
    float epsilon = 0.5f;
    Vector2 velocity = new Vector2(Game.character_delta_x, Game.character_delta_y);

    //migrate collision function here


//    public collision() {
//        int
//    }

    public movement(Boolean x_collided, Boolean y_collided) {
        velocity.y -= gravity;
        if (x_collided) {
            velocity.x = -1 * CoR * velocity.x;
            if (velocity.x < epsilon) {
                velocity.x = 0;
            }
        }
        if (y_collided) {
            velocity.y = -1 * CoR * velocity.y;
            if (velocity.y < epsilon) {
                velocity.y = 0;
            }
        }
    }

}
