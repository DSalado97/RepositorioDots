package com.example.golpea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.golpea.listeners.OnTouchEventListener;
import com.example.golpea.sprites.Bola;
import com.example.golpea.sprites.Sprite;

import java.util.LinkedList;

public class Principal extends GameView implements OnTouchEventListener {


    private final Context context;
    private final int x;
    private final int y;
    private boolean comienza=false;


    //Actores del juego
    Bola pj;

    float lineX1,lineY1, centroX, centroY;

    //variables del juego
    public int puntMax=0;
    public int nivel= 1;

    public Principal(Context context, int x, int y) {
        super(context,x,y);
        this.context = context;
        this.x = x;
        this.y = y;
        addOnTouchEventListener(this);

        pj = new Bola(this, this.getmScreenX()/2, this.getmScreenY()/2, 15, Color.WHITE);
        nuevos.add(pj);

    }

    public int random(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public void setupGame() {
        resetPj();

        for(int i=0;i<5;i++){
            generarBola();
        }

    }

    public void resetPj(){
        if(nivel==1){
            pj.puntuacion=0;
        }
        pj.radio=15;
        pj.centroX=this.getmScreenX()/2;
        pj.centroY=this.getmScreenY()/2;
        pj.setVisible(true);
        nuevos.add(pj);
    }

    public void generarBola(){
        int radioMin=10;
        int radioMax=10;

        if(pj.radio>10 && pj.radio<20){
            //radioMin=13;
            radioMax=15;
        } else if (pj.radio>20 && pj.radio<30){
            //radioMin=15;
            radioMax=30;
        } else if (pj.radio>30 && pj.radio<50){
            //radioMin=25;
            radioMax=50;
        } else if (pj.radio>50 && pj.radio<100){
            //radioMin=35;
            radioMax=100;
        } else if (pj.radio>100 && pj.radio<150){
            //radioMin=90;
            radioMax=150;
        }

        Bola a=new Bola(this, -90, random(0, this.getmScreenY()), random(radioMin, radioMax), Color.rgb(random(0, 255), random(0, 255), random(0, 255)));
        nuevos.add(a); a.setup();

        a=new Bola(this, this.getmScreenX()+90, random(0, this.getmScreenY()), random(radioMin, radioMax), Color.rgb(random(0, 255), random(0, 255), random(0, 255)));
        nuevos.add(a); a.setup();
    }

    //Realiza la lógica del juego, movimientos, física, colisiones, interacciones..etc
    @Override
    public void actualiza() {
        //actualizamos los actores
        for (Sprite actor : actores) {
            if (actor.isVisible()){
                actor.update();
            } else if(!actor.isVisible()){
                generarBola();
            }
        }

        actores.addAll(nuevos);
        nuevos=new LinkedList<>();

        if(pj.radio>=150){
            nivel++;
            actores.clear();
            comienza=false;
            resetPj();
        }

        if(!pj.isVisible()){
            actores.clear();
        }
    }

    //dibuja la pantalla
    @Override
    public void dibuja(Canvas canvas) {
        //se pinta desde la capa más lejana hasta la más cercana
        // Resolución de mi movil = 2130 x 1080
        paint.setTypeface(getResources().getFont(R.font.daydream));
        canvas.drawColor(Color.rgb(231,223,194));



        synchronized(actores) {
            for (Sprite actor : actores) {
                actor.pinta(canvas);
            }
        }
        //dibujamos puntuacion al finalizar partida.
        if(!pj.isVisible()){
            paint.setTextSize(65);
            paint.setColor(Color.WHITE);
            canvas.drawText("puntos: " + pj.puntuacion, this.getmScreenX()/8.5f, this.getmScreenY()/2, paint);
            if(pj.puntuacion>puntMax){
                puntMax=pj.puntuacion;
            }
            paint.setTextSize(50);
            paint.setColor(Color.rgb(218,165,32));
            canvas.drawText("Max: " + puntMax, this.getmScreenX()/2.8f, this.getmScreenY()/2+100, paint);
            comienza=false;
            nivel=1;
        }

        if(!comienza){
            paint.setTextSize(40);
            paint.setColor(Color.WHITE);
            canvas.drawText("Nivel "+nivel, this.getmScreenX()/2.5f, this.getmScreenY()/6, paint);

            paint.setTextSize(40);
            paint.setColor(Color.WHITE);
            canvas.drawText("Toca para jugar", this.getmScreenX()/4.5f, this.getmScreenY()/3, paint);
        }
    }

    //Responde a los eventos táctiles de la pantalla
    @Override
    public void ejecutaActionDown(MotionEvent event) {
        if(!comienza){
            setupGame();
            comienza=true;
        }

        lineX1=event.getX();
        lineY1=event.getY();
        centroX = pj.centroX;
        centroY = pj.centroY;
    }

    @Override
    public void ejecutaActionUp(MotionEvent event) {}

    @Override
    public void ejecutaMove(MotionEvent event) {
        pj.centroX=event.getX()-lineX1+centroX;
        pj.centroY=event.getY()-lineY1+centroY;


    }


}
