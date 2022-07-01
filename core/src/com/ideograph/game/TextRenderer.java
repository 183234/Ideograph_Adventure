package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TextRenderer {
    private static TextRenderer _instance = null;
    private FreeTypeFontGenerator gen;

    private TextRenderer(){
//        gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Oxygen-Regular.ttf"));
        gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Oxygen/Oxygen-Regular.ttf"));

    }

    public void render(SpriteBatch batch, String text, int x,int y) {
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 14;
        param.characters = text;
        BitmapFont font =gen.generateFont(param);
        font.draw(batch, text, x, y);
    }

    public static TextRenderer getInstance(){
        if(_instance == null){
            _instance = new TextRenderer();
        }
        return _instance;
    }

    public void dispose(){
        gen.dispose();
    }
}
