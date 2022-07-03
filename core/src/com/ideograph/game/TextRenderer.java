package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TextRenderer {
    public static final String CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/$:";
    public static final String CHAR_SET2 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/$:";
    public static final String CHAR_SET3 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/$:";
    private static TextRenderer _instance = null;
    private FreeTypeFontGenerator gen;
    private FreeTypeFontParameter param;
    private FreeTypeFontParameter param2;
    private FreeTypeFontParameter param3;
    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;


    private TextRenderer() {
        String font_path = "fonts/Lxgw-WenKai/TC/LXGWWenKaiTC-Regular.ttf";
        gen = new FreeTypeFontGenerator(Gdx.files.internal(font_path));
        param = new FreeTypeFontParameter();
        param2 = new FreeTypeFontParameter();
        param3 = new FreeTypeFontParameter();
        param.size = 16;
        param2.size = 48;
        param3.size = 32;
        param.characters = CHAR_SET;
        param2.characters = CHAR_SET2;
        param3.characters = CHAR_SET3;
        font = gen.generateFont(param);
        font2 = gen.generateFont(param2);
        font3 = gen.generateFont(param3);
    }

    public void draw(SpriteBatch batch, String text, float x, float y) {
        font.draw(batch, text, x, y);
    }

    public void draw2(SpriteBatch batch, String text, float x, float y) {
        font2.draw(batch, text, x, y);
    }

    public void draw3(SpriteBatch batch, String text, float x, float y) {
        font3.draw(batch, text, x, y);
    }

    public static TextRenderer getInstance() {
        if (_instance == null) {
            _instance = new TextRenderer();
        }
        return _instance;
    }

    public void dispose() {
        gen.dispose();
        font.dispose();
        font2.dispose();
        font3.dispose();
    }
}
