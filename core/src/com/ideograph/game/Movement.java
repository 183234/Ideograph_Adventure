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

    //determine if grounded
    public static void isGrounded() {
        if (Game.tiledLayer.getCell((int) Game.character_x / 72, (int) (Game.character_y / 72)) != null || Game.tiledLayer.getCell((int) (Game.character_x / 72) + 1, (int) (Game.character_y / 72)) != null) {
            grounded = (Math.abs(Game.character_delta_y) <= 5);
        } else {
            grounded = false;
        }
    }

    public static boolean isBlock(int x, int y) {
        return Game.tiledLayer.getCell(x, y) != null;
    }
    public static boolean inside(float l, float x, float r) {
        return l < x && x < r;
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
                        Math.min(r, br) <= Math.max(l, bl) ||
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
            Game.character_delta_y -= gravity;
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

        ArrayList<int[]> collisions = pixelCollision();
//        InteractiveBlock.Interact(collisions);
        //Collision_handling();


        //fall safe for debugging
        if (Game.character_y <= 0) {
            Game.character_y = 0;
            if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
                Game.character_delta_y = 0;
            }
        }

        //move character
        Game.character_x += Game.character_delta_x;
        Game.character_y += Game.character_delta_y;

    }

}