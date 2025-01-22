package com.example.golpea.listeners;

import android.view.MotionEvent;

public interface OnTouchEventListener {
    public abstract void ejecutaActionDown(MotionEvent event);
    public abstract void ejecutaActionUp(MotionEvent event);
    public abstract void ejecutaMove(MotionEvent event);
}
