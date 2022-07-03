package com.ideograph.game;

public class Attack {
    public int[] getDamageAttribute(Weapon weapon){
        return new int[]{weapon.aim, weapon.accuracy, weapon.consistency};
    }
    public void drawTrajectory(int aim){

    }
}

