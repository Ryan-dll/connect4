package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BoardGrid {

    private static Bitmap Slot = null;

    private static int id = R.drawable.empty_space;

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

    public static Bitmap getSlot() {
        return Slot;
    }

    public static void setSlot(Context context)
    {
        if (Slot == null)
        {
            Slot = BitmapFactory.decodeResource(context.getResources(), id);
        }
    }

    BoardGrid (float x, float y){
        // Slot = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = x;
        this.y = y;
    }



    public void onDraw(Canvas canvas, float marginX, float marginY,
                       int puzzleSize, float scaleFactor){

        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginY + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        //canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(Slot, 0, 0, null);
        canvas.restore();
    }
}
