package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {

        switch(event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent", "ACTION_DOWN");
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i("onTouchEvent", "ACTION_UP");
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }



        return false;
    }

}
