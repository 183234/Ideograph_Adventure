package com.ideograph.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ideograph.game.Game;
import com.ideograph.game.level_tutorial;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.foregroundFPS = 480;
            config.title = "Ideograph Adventure";
            config.width = 1920;
            config.height = 1080;
            config.resizable = false;
            new LwjglApplication(new Game(), config);
            //new LwjglApplication(new level_tutorial(), config);
            //new LwjglApplication(new Game(), config);
    }
}