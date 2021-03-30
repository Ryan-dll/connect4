package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Piece {

    /**
     * The size of the puzzle in pixels
     */
    private int gameSize = 1;

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

    private int id;

    private Bitmap piece;
    private Paint paint = new Paint();

    private boolean Player1 = true;

    public boolean isPlayer1() {
        return Player1;
    }

    public void setPlayer1(boolean player1) {
        Player1 = player1;
    }

    private float x;
    private float y;

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
//public ArrayList<Pieces> pieces = new ArrayList<Pieces>();

    public Piece(Context context, int id, float x, float y)
    {
        piece = BitmapFactory.decodeResource(context.getResources(), id);
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public Bitmap getPiece() {
        return piece;
    }

    public void onDraw(Canvas canvas, float marginX, float marginY,
                       float puzzleSize, float scaleFactor){

        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginY + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        //canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }

    /**
     * Move the puzzle piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }


    public void setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Test to see if we have touched a puzzle piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param boardSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       float boardSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = ((int)((testX - x) * boardSize ));
        int pY = ((int)((testY - y) * boardSize ));

        if(pX < 0 || pX >= (piece.getWidth()) ||
                pY < 0 || pY >= (piece.getHeight())) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }

    public boolean isGreen()
    {
        if (new Integer(id).equals(R.drawable.spartan_green))
        {
            return true;
        }
        return false;
    }


}
