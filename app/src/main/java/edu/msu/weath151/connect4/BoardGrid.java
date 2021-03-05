package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BoardGrid {

    private Bitmap Slot;

    private int id;

    private float x;

    private float y;

    int boardSize;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private boolean Taken = false;

    public boolean isTaken() {
        return Taken;
    }

    public void setTaken(boolean taken) {
        Taken = taken;
    }

    public Bitmap getSlot() {
        return Slot;
    }

    BoardGrid (Context context, int id, float x, float y){
        Slot = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = x;
        this.y = y;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(Slot, x, y, null);
    }
}
