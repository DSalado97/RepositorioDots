package com.example.golpea.listeners;

import com.example.golpea.sprites.Sprite;

public interface OnColisionListener {
    public final int TOP=0,BOTTOM=1,LEFT=2,RIGHT=3;
    public void onColisionEvent(Sprite s);
}
