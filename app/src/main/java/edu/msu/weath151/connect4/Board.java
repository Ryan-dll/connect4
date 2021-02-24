package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Board {

    /**
     * Percentage of the display width or height that
     * is occupied by the board.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling in the Board
     */
    private Paint fillPaint;

    private Pieces pieces = null;

    public Board()
    {
        init();
    }

    public Board(Context context){
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);
    }

    public void onDraw(Canvas canvas){
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int boardSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        int marginX = (wid - boardSize) / 2;
        int marginY = (hit - boardSize) / 2;

        canvas.drawRect(marginX, marginY,
                marginX + boardSize, marginY + boardSize, fillPaint);

        pieces.onDraw(canvas);
    }

    public void init()
    {
        pieces = new Pieces();
    }
}
