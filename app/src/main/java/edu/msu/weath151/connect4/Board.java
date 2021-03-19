package edu.msu.weath151.connect4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Board {

    /**
     * Percentage of the display width or height that
     * is occupied by the board.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    private float marginX;

    private float marginY;

    private int boardSize;

    private View boardView;

    private float scaleFactor;

    private Piece emptyPiece;
    private Piece greenPiece;


    private boolean Player1Turn = true;

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
    private Piece piece;
    private ArrayList<ArrayList<BoardGrid>> board_pieces = new ArrayList<ArrayList<BoardGrid>>();

    private BoardGrid Grid;

    private boolean FirstDraw = true;

    private Canvas canvas;

    public Board(Context context, View view) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);
        this.boardView = view;
        emptyPiece = new Piece(context, R.drawable.empty_space, 0, 0);
        greenPiece = new Piece(context, R.drawable.spartan_green, 0, 0);
        this.context = context;
        AssembleBoard(context);
    }


    private Context context;

    public float getMarginX() {
        return marginX;
    }

    public float getMarginY() {
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
            BoardGrid.setSlot(context);
            for (int j = 0; j < 6; j++) {
                BoardGrid BoardPiece = new BoardGrid(i*BoardGrid.getSlot().getWidth(), j*BoardGrid.getSlot().getHeight());
                column.add(BoardPiece);
            }
            board_pieces.add(column);
        }
    }
/*
    public void ReconfigureBoard(Canvas canvas, int MarginX, int MarginY){
        if (FirstDraw) {

            BoardGrid.setSlot(context);
            int wid = board_pieces.get(0).get(0).getSlot().getWidth();

            // Rows
            for (int i = 0; i < board_pieces.size(); i++) {

                // Columns
                for (int j = 0; j < board_pieces.get(i).size(); j++) {

                    BoardGrid boardGrid = new BoardGrid(i*BoardGrid.getSlot().getWidth(), j*BoardGrid.getSlot().getHeight());
                    board_pieces.add
                }
                // END Columns
            }
            // END Rows
        }
    }
*/
    public void onDraw(Canvas canvas){
        float wid = canvas.getWidth();
        float hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        float minDim = wid < hit ? wid : hit;

        boardSize = (int)(minDim * SCALE_IN_VIEW);

        // What scale fits both vertically and horizontally?
        float scaleH = wid/(emptyPiece.getPiece().getWidth()*7);
        float scaleV = hit/(emptyPiece.getPiece().getHeight()*6);

        // Use smaller
        scaleFactor = scaleH < scaleV ? scaleH : scaleV;

        // What is the scaled image size?
        float iWid = scaleFactor * emptyPiece.getPiece().getWidth()*7;
        float iHit = scaleFactor * emptyPiece.getPiece().getHeight()*6;

        // Compute the margins so we center the puzzle
        marginX = (wid - iWid) / 2;
        marginY = (hit - iHit) / 2;

        //ReconfigureBoard(canvas,0,0);

        // Draw the board
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);

        for( int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 6; j++){
                board_pieces.get(i).get(j).onDraw(canvas, 0, 0, 1, 1);
            }
        }

        pieces.onDraw(canvas, 0, 0, boardSize, 1);

        if (FirstDraw) {
            this.FirstDraw = false;
        }

        canvas.restore();

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

        float relX = (event.getX() ) / (boardSize*scaleFactor);
        float relY = (event.getY() ) / (boardSize*scaleFactor);

        canvas = new Canvas();
        greenPiece.onDraw(canvas, greenPiece.getPiece().getWidth(), greenPiece.getPiece().getHeight(), boardSize, 1);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                SnapPiece(relX, relY);
                view.invalidate();

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

    public void SnapPiece(float relX, float relY){
        for( ArrayList<BoardGrid> empty_pieces : board_pieces)
        {
            for (BoardGrid empty_piece : empty_pieces){
                int wid = BoardGrid.getSlot().getWidth();

                float BoardPieceX = (empty_piece.getX())/( scaleFactor);
                float BoardPieceY = empty_piece.getY()/( scaleFactor);

                float PieceX = dragging.getX();
                float PieceY = dragging.getY();
                float x = wid/((scaleFactor));

                double Distance = Math.sqrt(Math.pow(Math.abs(PieceX - BoardPieceX), 2) +
                        Math.pow(Math.abs(PieceY - BoardPieceY), 2));
                if(Distance < (wid)) {

                    dragging.setLocation(BoardPieceX ,BoardPieceY );

                    Player1Turn = !Player1Turn;
                }
            }
        }
        dragging = null;
    }

    /**
     * Handle a touch message. This is when we get an initial touch
     * @param x x location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @param y y location for the touch, relative to the puzzle - 0 to 1 over the puzzle
     * @return true if the touch is handled
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        for(int p=pieces.getPieces().size()-1; p>=0;  p--) {
            if( pieces.getPieces().get(p).hit(x, y, boardSize, scaleFactor)) {
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

    public void addPiece(Context context, int id){pieces.addPiece(context, id);}
}
