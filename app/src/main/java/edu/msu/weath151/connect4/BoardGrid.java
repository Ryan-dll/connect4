package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BoardGrid {

    private Bitmap Slot;

    private int id;

    float x;

    float y;

    BoardGrid (Context context, int BoardSize, int id){
        Slot = BitmapFactory.decodeResource(context.getResources(), id);
    }

    public void OnDraw(Canvas canvas) {

    }
}
