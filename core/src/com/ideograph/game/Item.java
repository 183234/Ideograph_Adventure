package com.ideograph.game;

import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

public class Item {
    protected String item_name;
    protected String item_description;
    protected Texture item_texture;

    public Item() {
        item_name = "Unnamed";
        item_description = "Nothing here";
    }
}

class Food extends Item {
    boolean hasConsumedBefore = false;
    int StaminaRegen, HealthRegen;
    //extra behaviors
    int[] Poison = {0, 0, 0}; //Strength, duration(frames), possibility(%)
    int[] Weakness = {0, 0, 0}; //Strength, duration(frames), possibility(%)
    int[] Strength = {0, 0, 0}; //Strength, duration(frames), possibility(%)
    int[] Swiftness = {0, 0, 0}; //Strength, duration(frames), possibility(%)

    public Food(String name, String desc) {
        this.item_name = name;
        this.item_description = desc;
        this.item_texture = new Texture(name+".png");
    }

    public String ExtraBehavior() {
        return "";
    }
}

class Weapon extends Item {
    int aim = 0; //determines trajectory length
    int consistency = 0; //damage spread
    int accuracy = 0; //determines randomness of initial velocity
    //damage_attributes
    int neutral_dmg, fire_dmg, water_dmg, earth_dmg, air_dmg, thunder_dmg;

    public Weapon() {
        super();
    }

    public void setup(int aim, int consistency, int accuracy, String item_description) {
        this.aim = aim;
        this.consistency = consistency;
        this.accuracy = accuracy;
        this.item_description = item_description;
    }

    public void setDamage(int neutral_dmg, int fire_dmg, int water_dmg, int earth_dmg, int air_dmg, int thunder_dmg) {
        this.neutral_dmg = neutral_dmg;
        this.fire_dmg = fire_dmg;
        this.water_dmg = water_dmg;
        this.earth_dmg = earth_dmg;
        this.air_dmg = air_dmg;
        this.thunder_dmg = thunder_dmg;
    }

}
