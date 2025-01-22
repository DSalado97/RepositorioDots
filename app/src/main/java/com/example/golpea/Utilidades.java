package com.example.golpea;

import android.graphics.Color;

import com.example.golpea.sprites.Bola;

public class Utilidades {

    public static float  distancia(float x1,float y1,float x2,float y2){

        return (float)Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2));
    }

    public static boolean colisionCirculos(Bola b1, Bola b2){

        if((b1.radio+b2.radio)<=distancia(b1.centroX,b1.centroY,b2.centroX, b2.centroY)) {
            return false;
        } else {
            if(b1.getColor()== Color.WHITE) {
                b2.setVisible(false);
            }
            return true;

        }
    }


}
