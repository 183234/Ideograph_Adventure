package com.ideograph.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemies {
    static final int ENEMY_WIDTH = 72;
    static final int ATTACK_CD = 20;

    static class Enemy {
        private int x, y;
        private final int bound_l, bound_r;
        private final Texture texture;
        private int speed;
        private int health;
        private boolean dead;

        public Enemy(JsonValue json) {
            this.x = json.get("x").asInt() * Game.BLOCK_WIDTH;
            this.y = json.get("y").asInt() * Game.BLOCK_WIDTH;
            this.speed = json.get("speed").asInt();
            this.bound_l = json.get("bound_left").asInt() * Game.BLOCK_WIDTH;
            this.bound_r = json.get("bound_right").asInt() * Game.BLOCK_WIDTH;
            String texture_path = json.get("texture").asString();
            this.texture = new Texture(texture_path);
            this.health = json.get("health").asInt();
            this.dead = false;
        }
//
//        public Enemy(int x, int y, int bound_l, int bound_r, String texture) {
//
//        }

        public void move() {
            if(this.x + speed < this.bound_l || this.x + speed > this.bound_r){
                this.speed = -this.speed;
            }
            this.x += speed;
        }

        public void draw(SpriteBatch batch) {
            batch.draw(this.texture, this.x, this.y);
        }

        public boolean collided(float x, float y, float width){
            return Math.min(this.x + ENEMY_WIDTH, x + width) > Math.max(this.x, x) &&
                    Math.min(this.y + ENEMY_WIDTH, y + width) > Math.max(this.y, y);
        }

        public void dispose(){
            texture.dispose();
        }
    }

    ArrayList<Enemy> enemies;
    int attack_cd;
    int be_attacked_cd;
    int attack_count;

    private Enemies(){
        this.enemies = new ArrayList<Enemy>();
        this.attack_count = 0;
    }

    static Enemies getEnemies(int level){
        JsonReader reader = new JsonReader();
        JsonValue json_all = reader.parse(Gdx.files.internal("level/enemies_"+level+".json"));
        Enemies res = new Enemies();
        for(JsonValue json : json_all){
            res.enemies.add(new Enemy(json));
        }
        return res;
    }

    public void render(SpriteBatch batch){
        for(Enemy enemy : this.enemies) {
            if(!enemy.dead) {
                enemy.move();
                enemy.draw(batch);
            }
        }
    }

    public void attack() {
        if(this.attack_cd > 0) {
            this.attack_cd--;
        }
        for(Enemy enemy : this.enemies){
            if(enemy.collided(Game.character_x, Game.character_y, Game.CHAR_WIDTH) && this.attack_cd == 0 && !enemy.dead) {
                this.attack_cd = ATTACK_CD;
                Game.health_cur -= 25;
                attack_count++;
//                System.out.println("attacked! " + attack_count);
            }
        }
    }

    public void be_attacked() {
        if(this.be_attacked_cd > 0){
            this.be_attacked_cd--;
        }
        for(Enemy enemy : this.enemies){
            for(Game.Attack atk : Game.attacks){
                if(enemy.collided(atk.x, atk.y,  49) && this.be_attacked_cd == 0){
                    enemy.health = Math.max(0, enemy.health-atk.dmg);
                    this.be_attacked_cd = ATTACK_CD;
                }
            }
            if(enemy.health == 0){
                enemy.dead = true;
            }
        }
    }

    public void dispose() {
        for(Enemy enemy : this.enemies) {
            enemy.dispose();
        }
    }
}
