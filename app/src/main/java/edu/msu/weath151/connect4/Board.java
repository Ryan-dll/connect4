package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Board {

    /**
     * Percentage of the display width or height that
     * is occupied by the board.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    private int marginX;

    private int marginY;

    private int boardSize;

    /**
     * Paint for filling in the Board
     */
    private Paint fillPaint;

    private Pieces pieces = null;

    private ArrayList<Piece> empty_pieces = new ArrayList<Piece>();

    public Board()
    {
        init();
    }


    public Board(Context context){
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);

        empty_pieces.add(new Piece(context, R.drawable.empty_space, 0, 0));


    }

    public void onDraw(Canvas canvas){
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        marginX = (wid - boardSize) / 2;
        marginY = (hit - boardSize) / 2;

        canvas.drawRect(marginX, marginY,
                marginX + boardSize, marginY + boardSize, fillPaint);


        for( Piece empty_piece : empty_pieces)
        {
            empty_piece.onDraw(canvas);
        }

        pieces.onDraw(canvas);


    }


    public void init()
    {
        pieces = new Pieces();
    }


}
