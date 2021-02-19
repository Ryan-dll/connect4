package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Piece {
    private Bitmap bitmap = null;
    private Paint paint = new Paint();

    float x;

    float y;

    public Piece(){
        paint.setColor(Color.BLACK);
    }

    public Piece(Context context, int id, float x, float y)
    {
        bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = x;
        this.y = y;
    }

    public void onDraw(Canvas canvas){

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
