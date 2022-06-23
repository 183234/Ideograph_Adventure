package com.ideograph.game;

public class character {
    double acceleration = 2;
    static double x = 0;
    static double delta_x = 0;
    static double y = 0;
    static double delta_y = 0;

    public void friction() {
        delta_x = delta_x * 0.9;
        delta_y = delta_y * 0.9;
        if(delta_x * delta_x < 1){
            delta_x = 0;
        }
        if(delta_y * delta_y < 1){
            delta_y = 0;
        }
    }
}
