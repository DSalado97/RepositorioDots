package com.example.golpea.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.example.golpea.GameView;
import com.example.golpea.Principal;
import com.example.golpea.Utilidades;
import com.example.golpea.listeners.OnColisionListener;

public class Bola extends Sprite implements OnColisionListener {

    private Principal game;
    public float centroX,centroY,radio;
    public boolean activa=true;
    public float rozamiento= (float) 0.98;
    public int puntuacion;

    public Bola(GameView game, int x, int y, int r, int color) {
        super(game);
        this.game=(Principal)game;
        centroX=x;
        centroY=y;
        radio=r;
        this.color=color;
    }

    @Override
    public void setup() {
        this.velActualX=random(-6,5);
        this.velActualY=random(-6,5);
       /* this.velActualX=velInicialX* game.factor_mov;
        this.velActualY=velInicialY* game.factor_mov;
        */

    }

    @Override
    public boolean colision(Sprite s){
        Bola b=(Bola)s;
        boolean col= Utilidades.colisionCirculos(this ,b);
        if(!col) activa=true;
        return col;
    }

    public int random(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    @Override
    public void update() {
        if(color != Color.WHITE) {
            centroX += velActualX;
            centroY += velActualY;
        }
        //Comprobamos colisiones con los bordes y entre los actores
        onFireColisionSprite();
        onFireColisionBorder();
        //Se actualizan otras variables internas
    }


    @Override
    public void onFireColisionBorder(){
        if(this.color==Color.WHITE) {
            if (this.centroX - radio < 30) {
                this.centroX += 5;
            }
            if (this.centroX + radio > game.getmScreenX() - 30) {
                this.centroX -= 5;
            }
            if (this.centroY - radio < 30) {
                this.centroY += 5;
            }
            if (this.centroY + radio > game.getmScreenY() - 30) {
                this.centroY -= 5;
            }
        } else {
            if (this.centroX > game.getmScreenX() + 100){
                centroX=-90;
            } else if(this.centroX < -100){
                centroX=game.getmScreenX()+90;
            } else if(this.centroY > game.getmScreenY() + 100){
                centroY=-90;
            } else if (this.centroY < -100){
                centroY=game.getmScreenY()+90;
            }
        }
    }

    @Override
    public void onColisionEvent(Sprite s) {
        if (s instanceof Bola) {
            if(this.color == Color.WHITE){
                if(this.radio>=((Bola) s).radio){
                    this.puntuacion++;
                    this.radio+=1;
                } else if(this.radio<((Bola) s).radio){
                    this.setVisible(false);
                }
            }
        }
    }

    @Override
    public  void pinta(Canvas canvas){
        paint.setColor(color);
        //paint.setStrokeWidth(8);
        // paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centroX,centroY,radio, paint);
    }

    @Override
    public String toString() {
        return "Bola{" +
                "color=" + color +
                '}';
    }
}
