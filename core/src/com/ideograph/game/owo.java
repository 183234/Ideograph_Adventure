package com.ideograph.game;

//health+stamina+ExtraBehaviors handling
public class owo {
    float hp = Game.health_cur;
    float sta = Game.stamina_cur;
}

class ExtraBehavior extends owo {
    public ExtraBehavior() {
        super();
    }

    //apply change in hp, amount is signed, time unit are frames
    public void hp_effect(int amount,int duration) {
        hp += amount;
    }
}