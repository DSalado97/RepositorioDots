package com.example.golpea;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    GameView juego;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //obtenemos la pantalla
        Display display= getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);

        juego=new Principal(this,size.x,size.y);
        setContentView(juego);
    }


    @Override
    protected void onResume() {
        super.onResume();
        juego.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        juego.pause();
    }
}