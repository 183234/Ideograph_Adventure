package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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

    public static void Collision_handling() {
//        if (Game.tiledLayer0.getCell((int) Game.character_x / 72, (int) (Game.character_y / 72)-1) != null || Game.tiledLayer0.getCell((int) (Game.character_x / 72), (int) (Game.character_y / 72)+1) != null) {
//            return true;
//        }
//        return false;
//        Rectangle left_bound = new Rectangle(Game.character_x, Game.character_y, 1, 64);
//        Rectangle right_bound = new Rectangle(Game.character_x+64, Game.character_y, 1, 64);
        float character_x_displaced, character_y_displaced;
        float x_dist, y_dist, x_time = 49, y_time = 49;
        if (velocity.x > 0 && velocity.y > 0) {
            for (int i = ((int) Game.character_x / 72); i < ((int) Game.character_x / 72) + 3; i++) {
                for (int j = ((int) Game.character_y / 72) * 72; j < ((int) Game.character_y / 72) * 72 + 3; j++)
                    if (Game.tiledLayer0.getCell(i, j) != null) {
                        //collision with velocity at first quadrant -> determine which side collided (-> zero out the one with higher value)
                        x_dist = i * 72 - Game.character_x - 64; //distance to wall on x axis
                        y_dist = j * 72 - Game.character_y - 64; //distance to wall on y axis
                        //calculate 'minimum time' required to reach wall -> collision priority
                        if (x_time == 49) {
                            x_time = x_dist / velocity.x;
                        } else if (x_dist / velocity.x < x_time && x_dist / velocity.x >= 0) {
                            x_time = x_dist / velocity.x;
                        }
                        if (y_time == 49) {
                            y_time = y_dist / velocity.y;
                        } else if (y_dist / velocity.y < y_time && y_dist / velocity.y >= 0) {
                            y_time = y_dist / velocity.y;
                        }
                    }
                //move character for (x_time+y_time)/2 frames, if collision then do collision on first side, if no second
                float avg_displace_time = (x_time + y_time) / 2;
                character_x_displaced = Game.character_x + velocity.x * avg_displace_time;
                character_y_displaced = Game.character_y + velocity.y * avg_displace_time;


            }
        }
        //other velocity situation...
    }

    public static boolean yCollided() {
        // true -> collision happens

        if (Game.tiledLayer0.getCell((int) Game.character_x / 72 - 1, (int) (Game.character_y / 72)) != null || Game.tiledLayer0.getCell((int) (Game.character_x / 72) + 1, (int) (Game.character_y / 72)) != null) {
            return true;
        }
        return false;
    }

    public static void doMovement() {
        isGrounded();
        if (!grounded) {
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

        Collision_handling();


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


//        if (xCollided()) {
//            if (velocity.x > 0) {
//                Game.character_x = ((int) Game.character_x / 72) * 72 + 8;
//            }
//            velocity.x = -1 * CoR * velocity.x;
//            if (velocity.x < epsilon) {
//                velocity.x = 0;
//            }
//
//        }
//        if (yCollided()) {
//            velocity.y = -1 * CoR * velocity.y;
//            if (velocity.y < epsilon) {
//                velocity.y = 0;
//            }
//        }

    }

}
