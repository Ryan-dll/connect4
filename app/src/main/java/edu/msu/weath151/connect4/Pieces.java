package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Pieces {

    private ArrayList<Piece> pieces = new ArrayList<>();

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    float x;
    float y;

    public Pieces(Context context)
    {
    }

    public void onDraw(Canvas canvas, float MarginX, float MarginY,float BoardSize, float Scale ) {
        for( Piece piece : pieces)
        {
            piece.onDraw(canvas,MarginX,MarginY,BoardSize,Scale);
        }
    }

    public void remove(Piece piece) {
        pieces.remove(piece);
    }

    public void setLocation(float a, float b) {
        x = a;
        y = b;
    }

    public void addPiece(Context context, int id){pieces.add(new Piece(context, id, x, y));}
}

