package com.ideograph.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;


public class Attack {
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();

    public int[] getDamageAttribute(Weapon weapon){
        return new int[]{weapon.aim, weapon.accuracy, weapon.consistency};
    }

//    public static void drawline(Vector2 start, Vector2 end, int lineWidth, Color color, Matrix4 projectionMatrix){
//        Gdx.gl.glLineWidth(2);
//        shapeRenderer.setProjectionMatrix(projectionMatrix);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(color);
//        shapeRenderer.line(start, end);
//        shapeRenderer.end();
//        Gdx.gl.glLineWidth(1);
//    }
//
//    public static void drawTrajectory(int aim){
//        drawline(new Vector2( 490,490), new Vector2(10, 10), 5, Color.CHARTREUSE, Game.camera.projection);
//    }
}

