package com.example.golpea.sprites;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.golpea.GameView;
import com.example.golpea.listeners.OnColisionListener;

public abstract class Sprite implements OnColisionListener {

    private GameView game;
    float mXCoord;
    float mYCoord;
    float velActualX;
    float velActualY;
    boolean visible;
    int color;
    Paint paint;


    public float getmXCoord() {
        return mXCoord;
    }
    public void setmXCoord(float mXCoord) {
        this.mXCoord = mXCoord;
    }
    public float getmYCoord() {
        return mYCoord;
    }
    public void setmYCoord(float mYCoord) {
        this.mYCoord = mYCoord;
    }

    public float getVelActualX() {
        return velActualX;
    }

    public void setVelActualX(float velActualX) {
        this.velActualX = velActualX;
    }

    public float getVelActualY() {
        return velActualY;
    }

    public void setVelActualY(float velActualY) {
        this.velActualY = velActualY;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public GameView getGame() {
        return game;
    }
    public void setGame(GameView game) {
        this.game = game;
    }

    public Sprite(GameView game){
        this.game=game;
        visible=true;
        paint=new Paint();
    }

    public boolean isVisible() {
        return visible;
    }

    public  void onFireColisionSprite(){
        for (Sprite objeto : GameView.actores) {
            if (!objeto.equals(this)) {
                if (objeto.isVisible() && colision(objeto))
                    onColisionEvent(objeto);
            }
        }
    }
    public  abstract void onFireColisionBorder();
    public abstract boolean colision(Sprite s);

    public  abstract void pinta(Canvas canvas);

    public void recolocaX(float x) { }
    public void recolocaY(float y){ }
    public void recolocaXY(float x,float y){ }

    public abstract void setup();
    public abstract void update();



}
