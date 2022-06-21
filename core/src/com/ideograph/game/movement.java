package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class movement {
    static boolean grounded;
    static float speed_factor = 0.5f;
    static float gravity = 0.5f;
    float CoR = 0.8f; //coefficient of Restitution
    float epsilon = 0.5f;
    static int soft_jump_grace_period = 2;
    static int high_jump_period = 6;
    static Vector2 velocity = new Vector2(0, 0);


    //migrate collision function here


    //determine if grounded
    public static void isGrounded() {
        if (Game.tiledLayer0.getCell((int) Game.character_x / 72, (int) (Game.character_y / 72)) != null || Game.tiledLayer0.getCell((int) (Game.character_x / 72) + 1, (int) (Game.character_y / 72)) != null) {
            Game.character_y = Game.character_y / 72 * 72;
            if (velocity.y <= 5) {
                grounded = true;
            }
        } else {
            grounded = false;
        }
    }

    public static boolean xCollided(){
        // true -> collision happens

        if (Game.tiledLayer0.getCell((int) Game.character_x / 72, (int) (Game.character_y / 72)-1) != null || Game.tiledLayer0.getCell((int) (Game.character_x / 72), (int) (Game.character_y / 72)+1) != null) {
            return true;
        }
        return false;
    }

    public static boolean yCollided(){
        // true -> collision happens

        if (Game.tiledLayer0.getCell((int) Game.character_x / 72-1, (int) (Game.character_y / 72)) != null || Game.tiledLayer0.getCell((int) (Game.character_x / 72)+1, (int) (Game.character_y / 72)) != null) {
            return true;
        }
        return false;
    }

    public static void doMovement() {
        isGrounded();
        if(!grounded) {
            velocity.y -= gravity;
        }

        //fall safe for debugging
        if (Game.character_y <= 0) {
            grounded = true;
        }
        if (grounded) soft_jump_grace_period = 4;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Game.stamina_cur > 0) {
            speed_factor = 1;
            Game.stamina_cur--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x -= 1 * speed_factor;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x += 1 * speed_factor;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) velocity.y -= 1 * sprint;
        if (Gdx.input.isKeyPressed(Input.Keys.W) && grounded) {
            velocity.y = 18;
        }
        if (!grounded) {
            if (soft_jump_grace_period >= 0) {
                soft_jump_grace_period--;
            } else {
                if (Gdx.input.isKeyPressed(Input.Keys.W) && high_jump_period >= 0) {
                    velocity.y += 2;
                    high_jump_period--;
                }
            }
        } else {
            soft_jump_grace_period = 4;
            high_jump_period = 3;
        }

        //apply friction and gravity
        velocity.y -= gravity;
        velocity.x *= 0.9;

        //fall safe for debuging
        if (Game.character_y <= 0) {
            Game.character_y = 0;
            if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
                velocity.y = 0;
            }
        }

        //move character
        Game.character_x += velocity.x;
        Game.character_y += velocity.y;


        // try to code new collision
        /*



        */



        if (xCollided()) {
            if(velocity.x > 0){
                Game.character_x = ((int) Game.character_x/72) * 72 + 8;
            }
            velocity.x = -1 * CoR * velocity.x;
            if (velocity.x < epsilon) {
                velocity.x = 0;
            }

        }
        if (yCollided()) {
            velocity.y = -1 * CoR * velocity.y;
            if (velocity.y < epsilon) {
                velocity.y = 0;
            }
        }

    }

}
