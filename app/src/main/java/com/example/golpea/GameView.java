package com.example.golpea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.golpea.listeners.OnTouchEventListener;
import com.example.golpea.sprites.Sprite;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public abstract class GameView extends SurfaceView implements Runnable {

    //hilo para realizar el bucle del juego
    Thread hilo = null;
    //superfice general para dibujar en ella
    SurfaceHolder mSurfaceHolder;
    //variable que se debe conocer dentro y fuera del hilo (volatile)
    protected volatile boolean enEjecucion;
    //Variable para saber si el juego está pausado
    public boolean pausado = false;

    //Canvas y pintura del dibujo
    public Canvas canvas;
    public Paint paint;
    //Tamaño de la pantalla en píxeles
    private int mScreenX, mScreenY;

    public int getmScreenX() {
        return mScreenX;
    }

    public void setmScreenX(int mScreenX) {
        this.mScreenX = mScreenX;
    }

    public int getmScreenY() {
        return mScreenY;
    }

    public void setmScreenY(int mScreenY) {
        this.mScreenY = mScreenY;
    }

    //Guardar los FPS del videojuego
    long FPS;
    private long ultimoProceso=0;
    private static int PERIODO_PROCESO=100;
    public float factor_mov=1;
    long ahora, tiempo_transcurrido;

    OnTouchEventListener listener;

    public static LinkedList<Sprite> actores=new LinkedList<>();
    public static LinkedList<Sprite> nuevos=new LinkedList<>();

    public GameView(Context context, int x, int y) {
        super(context);
        //inicializar tamaño de pantalla
        mScreenX = x;
        mScreenY = y;
        //inicializar objetos de dibujo
        mSurfaceHolder = this.getHolder();
        paint = new Paint();
    }

    public void addOnTouchEventListener(OnTouchEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        while (enEjecucion) {
            if (!pausado) {
                update();
            }
        }
    }

    public void update(){
        calculaFPS();
        actualiza();
        limpia();
        draw();

    }

    private void calculaFPS(){
        ahora=System.currentTimeMillis();       //100000
        if(ultimoProceso+PERIODO_PROCESO>ahora){   //0+200>100000?? No
            return;
        }
        tiempo_transcurrido=ahora-ultimoProceso;  //tiempo_transcurrido=1000000-0
        factor_mov=(tiempo_transcurrido)/PERIODO_PROCESO; //factor_mov=100000 el primero no es realista
        ultimoProceso=ahora;
    }

    //reanuda la partida
    public void resume() {
        enEjecucion = true;
        hilo = new Thread(this);
        hilo.start();
    }

    //pausa la partida
    public void pause() {
        enEjecucion = false;
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void onFireColision(){
        for (int i=0;i<actores.size()-1;i++)
            if(actores.get(i).isVisible()) {
                Sprite actor=actores.get(i);
                for (int j=i+1;j<actores.size();j++){
                    if (actores.get(j).isVisible() && actor.colision(actores.get(j)))
                        actor.onColisionEvent(actores.get(j));
                }
            }
    }
    synchronized public void limpia(){
        for (int i=0;i<actores.size();i++)
            if(!actores.get(i).isVisible()) {
                actores.remove(i);
            }
    }
    public void draw(){
        //comprobar si la superficie a pintar es válida
        if (mSurfaceHolder.getSurface().isValid()) {
            //Se empieza a pintar. Hay que bloquear el canvas a pintar
            canvas = mSurfaceHolder.lockCanvas();
            dibuja(canvas);
            // Se desbloquea el canvas añadiendo lo que se ha pintado
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    //GESTIÓN DE LAS ACCIONES DEL USUARIO SOBRE LA PANTALLA
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                listener.ejecutaActionDown(event);
                break;
            case MotionEvent.ACTION_UP:
                listener.ejecutaActionUp(event);
                break;
            case MotionEvent.ACTION_MOVE:
                listener.ejecutaMove(event);
                break;
        }
        return true;
    }

    public abstract void dibuja(Canvas canvas);
    protected abstract void actualiza();

}
