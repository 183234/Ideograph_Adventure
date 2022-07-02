package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TextRenderer {
    public static final String CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static TextRenderer _instance = null;
    private FreeTypeFontGenerator gen;
    private FreeTypeFontParameter param;
    private BitmapFont font;

    private TextRenderer() {
        String font_path = "fonts/Lxgw-WenKai/TC/LXGWWenKaiTC-Regular.ttf";
        gen = new FreeTypeFontGenerator(Gdx.files.internal(font_path));
        param = new FreeTypeFontParameter();
        param.size = 16;
        param.characters = CHAR_SET;
        font = gen.generateFont(param);
    }

    public void draw(SpriteBatch batch, String text, float x, float y) {
        font.draw(batch, text, x, y);
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
    }
}
