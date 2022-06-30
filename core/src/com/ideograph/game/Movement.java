package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.ArrayList;

public class Movement {
    static boolean grounded;
    static float speed_factor = 0.5f;
    static float gravity = 1.0f;
    float CoR = 0.8f; //coefficient of Restitution
    float epsilon = 0.5f;
    static int soft_jump_grace_period = 2;
    static int high_jump_period = 6;
    static float last_x, last_y;

    public static boolean isBlock(int x, int y) {
        return Game.tiledLayer.getCell(x, y) != null;
    }
    public static boolean inside(float l, float x, float r) {
        return l <= x && x <= r;
    }
    public static boolean intersect(float l1, float l2, float r1, float r2) {
        return inside(l1, l2, r1) && inside(l2, r1, r2);
    }
    public static String str(int x,int y) {
        return "(" + x + ", " + y + ")";
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
        float px = Game.character_x;
        float py = Game.character_y;


        ArrayList<int[]> collisions = new ArrayList<int[]>();

        int block_x0 = (int)(px/BLOCK);
        int block_y0 = (int)(py/BLOCK);
        grounded = false;
        for(int bx = block_x0 - 2; bx <= block_x0 + 2; bx++) {
            for(int by = block_y0 - 2; by <= block_y0 + 2; by++) {
                float l = px;
                float r = px + CHAR;
                float d = py;
                float u = py + CHAR;

                int bl = bx * BLOCK;
                int br = bx * BLOCK + BLOCK;
                int bb = by * BLOCK;
                int bu = by * BLOCK + BLOCK;

                if(!isBlock(bx, by) ||
                        Math.min(r, br) < Math.max(l, bl) ||
                        Math.min(u, bu) < Math.max(d, bb)) {
                    continue;
                }

                // collided!
                collisions.add(new int[]{bx, by});
//                System.out.println("block: " + str(bx, by));
                if(vx > 0 && intersect(last_x + CHAR, bl, r, br) && !isBlock(bx - 1, by)) {
                    System.out.println("collided in right face");
                    vx = 0;
                    px = Math.min(px, bl - CHAR);
                }
                if(vx < 0 && intersect(bl, l, br, last_x) && !isBlock(bx + 1, by)) {
                    System.out.println("collided in left face");
                    vx = 0;
                    px = Math.max(px, br);
                }
                if(vy > 0 && intersect(last_y + CHAR, bb, u, bu) && !isBlock(bx, by - 1)) {
//                    System.out.println("collided in upper face");
                    vy = 0;
                    py = Math.min(py, bb - CHAR);
                }
                if(vy < 0 && intersect(bb, d, bu, last_y) && !isBlock(bx, by + 1)) {
//                    System.out.println("collided in lower face");
                    vy = 0;
                    py = Math.max(py, bu);
                    grounded = true;
                }
            }
        }


        Game.character_x = px;
        Game.character_y = py;
        Game.character_delta_x = vx;
        Game.character_delta_y = vy;

        return collisions;
    }

    public static void doMovement() {
        Game.character_delta_y -= gravity;

        //fall safe for debugging
        if (Game.character_y <= 0) {
            grounded = true;
        }
        if (grounded) soft_jump_grace_period = 4;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Game.stamina_cur > 0) {
            speed_factor = 1;
            Game.stamina_cur--;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) Game.character_delta_x -= 1 * speed_factor;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) Game.character_delta_x += 1 * speed_factor;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) Game.character_delta_y -= 1 * sprint;
        if (Gdx.input.isKeyPressed(Input.Keys.W) && grounded) {
            Game.character_delta_y = 18;
        }
        if (!grounded) {
            if (soft_jump_grace_period >= 0) {
                soft_jump_grace_period--;
            } else {
                if (Gdx.input.isKeyPressed(Input.Keys.W) && high_jump_period >= 0) {
                    Game.character_delta_y += 2;
                    high_jump_period--;
                }
            }
        } else {
            soft_jump_grace_period = 4;
            high_jump_period = 3;
        }

        //apply friction and gravity
        Game.character_delta_x *= 0.9;

        //fall safe for debugging
        if (Game.character_y <= 0) {
            Game.character_y = 0;
            if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
                Game.character_delta_y = 0;
            }
        }

        ArrayList<int[]> collisions = pixelCollision();
//        InteractiveBlock.Interact(collisions);
        //Collision_handling();

        last_x = Game.character_x;
        last_y = Game.character_y;

        //move character
        Game.character_x += Game.character_delta_x;
        Game.character_y += Game.character_delta_y;

    }

}