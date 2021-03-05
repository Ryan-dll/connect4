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

    private View boardView;

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

    private ArrayList<ArrayList<BoardGrid>> board_pieces = new ArrayList<ArrayList<BoardGrid>>();

    private BoardGrid Grid;

    private boolean FirstDraw = true;


    public Board(Context context, View view){
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);
        this.boardView = view;

        AssembleBoard(context);
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

    public ArrayList<ArrayList<BoardGrid>> getBoard_pieces() {
        return board_pieces;
    }

    public void AssembleBoard(Context context) {
        for (int i = 0; i < 7; i++) {
            ArrayList<BoardGrid> column = new ArrayList<BoardGrid>();
            for (int b = 0; b < 6; b++) {
                BoardGrid BoardPiece = new BoardGrid(context, R.drawable.empty_space, 0, 0);
                column.add(BoardPiece);
            }
            board_pieces.add(column);
        }
    }

    public void ReconfigureBoard(Canvas canvas, int MarginX, int MarginY){
        if (FirstDraw) {
            int wid = board_pieces.get(0).get(0).getSlot().getWidth();

            // Rows
            for (int i = 0; i < board_pieces.size(); i++) {

                // Columns
                for (int b = 0; b < board_pieces.get(i).size(); b++) {

                    // Set X Coordinate
                    float PrevX = 0;
                    if (i > 0) {
                        PrevX = board_pieces.get(i-1).get(b).getX();
                        board_pieces.get(i).get(b).setX(PrevX + wid);
                    } else {
                        board_pieces.get(i).get(b).setX(MarginX);
                    }

                    // Set Y Coordinate
                    float PrevY = 0;
                    if (b > 0) {
                        PrevY = board_pieces.get(i).get(b-1).getY();
                        board_pieces.get(i).get(b).setY(PrevY + wid);
                    } else {
                        board_pieces.get(i).get(b).setY(MarginY);
                    }

                }
                // END Columns
            }
            // END Rows
        }
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

        ReconfigureBoard(canvas,marginX,marginY);

        for( ArrayList<BoardGrid> empty_pieces : board_pieces)
        {
            for (BoardGrid empty_piece : empty_pieces){
                empty_piece.onDraw(canvas);
            }
        }

        pieces.onDraw(canvas, marginX, marginY, boardSize, scaleFactor);

        if (FirstDraw) {
            this.FirstDraw = false;
        }

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
