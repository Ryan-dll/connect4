package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

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

    private float scaleFactor;

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private Piece dragging = null;

    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    /**
     * Paint for filling in the Board
     */
    private Paint fillPaint;

    private Pieces pieces;

    private ArrayList<Piece> board_pieces = new ArrayList<>();

    private BoardGrid Grid;


    public Board(Context context){
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);

        board_pieces.add(new Piece(context, R.drawable.empty_space, 0, 0));

        Grid = new BoardGrid(context, boardSize, R.drawable.empty_space);


    }

    public int getMarginX() {
        return marginX;
    }

    public int getMarginY() {
        return marginY;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public ArrayList<Piece> getBoard_pieces() {
        return board_pieces;
    }

    public void onDraw(Canvas canvas){
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int)(minDim * SCALE_IN_VIEW);

        scaleFactor = 1;

        // Compute the margins so we center the puzzle
        marginX = (wid - boardSize) / 2;
        marginY = (hit - boardSize) / 2;

        canvas.drawRect(marginX, marginY,
                marginX + boardSize, marginY + boardSize, fillPaint);


        for( Piece empty_piece : board_pieces)
        {
            empty_piece.onDraw(canvas, marginX, marginY, boardSize, scaleFactor);
        }

        pieces.onDraw(canvas, marginX, marginY, boardSize, scaleFactor);


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

        float relX = (event.getX() - marginX) / boardSize;
        float relY = (event.getY() - marginY) / boardSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //return onReleased(view, relX, relY);

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();

                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        for(int p=pieces.getPieces().size()-1; p>=0;  p--) {
            if(pieces.getPieces().get(p).hit(x, y, boardSize, scaleFactor)) {
                // We hit a piece
                dragging = pieces.getPieces().get(p);
                pieces.getPieces().remove(p);
                pieces.getPieces().add(dragging);
                lastRelX = x;
                lastRelY = y;
                return true;
            }
        }

        return false;
    }


}
