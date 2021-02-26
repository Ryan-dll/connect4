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

import java.util.ArrayList;

public class Piece {

    /**
     * The size of the puzzle in pixels
     */
    private int pieceSize;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * Top margin in pixels
     */
    private int marginY;

    private Bitmap bitmap = null;
    private Paint paint = new Paint();

    float x;

    float y;

    public ArrayList<Pieces> pieces = new ArrayList<Pieces>();


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
        //
        // Convert an x,y location to a relative location in the
        // puzzle.
        //

        float relX = (event.getX() - marginX) / pieceSize;
        float relY = (event.getY() - marginY) / pieceSize;


        switch(event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }



        return false;
    }



}
