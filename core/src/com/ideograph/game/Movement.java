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

    // handles collision
    // updates position & velocity
    // return: ArrayList<int[]{x, y}> - collided blocks
    public static ArrayList<int[]> pixelCollision(){
        // constants
        int BLOCK = Game.BLOCK_WIDTH;
        int CHAR = Game.CHAR_WIDTH;

        // current position & velocity
        float vx = Game.character_delta_x;
        float vy = Game.character_delta_y;

        float l = Game.character_x;
        float r = Game.character_x + CHAR;
        float d = Game.character_y;
        float u = Game.character_y + CHAR;

        ArrayList<int[]> collisions = new ArrayList<int[]>();

        int block_x0 = (int)(l/BLOCK);
        int block_y0 = (int)(d/BLOCK);
        for(int bx = block_x0 - 2; bx <= block_x0 + 2; bx++) {
            for(int by = block_y0 - 2; by <= block_y0 + 2; by++) {
                int bl = bx * BLOCK;
                int br = bx * BLOCK + BLOCK;
                int bb = by * BLOCK;
                int bu = by * BLOCK + BLOCK;

                if(!isBlock(bx, by) ||
                        Math.min(r,br) <= Math.max(l, bl) ||
                        Math.min(u, bu) <= Math.max(d, bb)) {
                    continue;
                }

                // collided!
                collisions.add(new int[]{bx, by});
                System.out.println("block: " + str(bx, by));

                float rewind = 100;
                int dir = 0;

                if(vx > 0 && inside(bl, r, br) && !isBlock(bx - 1, by)) {
                    System.out.println("collided in right face");
                    float re = (r - bl) / vx;
                    if(re < rewind){
                        rewind = re;
                        dir = 1;
                    }
                }
                if(vx < 0 && inside(bl, l, br) && !isBlock(bx + 1, by)) {
                    System.out.println("collided in left face");
                    float re = (l - br) / vx;
                    if(re < rewind) {
                        rewind = re;
                        dir = 2;
                    }
                }
                if(vy > 0 && inside(bb, u, bu) && !isBlock(bx, by - 1)) {
                    System.out.println("collided in upper face");
                    float re = (u - bb) / vy;
                    if(re < rewind){
                        rewind = re;
                        dir = 3;
                    }
                }
                if(vy < 0 && inside(bb, d, bu) && !isBlock(bx, by + 1)) {
                    System.out.println("collided in lower face");
                    float re = (d - bu) / vy;
                    if(re < rewind) {
                        rewind = re;
                        dir = 4;
                    }
                }

                l -= vx * rewind;
                r -= vx * rewind;
                u -= vy * rewind;
                d -= vy * rewind;
                if(dir == 1 || dir == 2) {
                    vx = 0;
                }else if(dir == 3 || dir == 4) {
                    vy = 0;
                }
            }
        }


        Game.character_x = l;
        Game.character_y = d;
        Game.character_delta_x = vx;
        Game.character_delta_y = vy;

        return collisions;
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

        ArrayList<int[]> collisions = pixelCollision();
//        InteractiveBlock.Interact(collisions);
        //Collision_handling();


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