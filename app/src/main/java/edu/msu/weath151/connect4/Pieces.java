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

    public Pieces(Context context)
    {
        Piece piece = new Piece(context, R.drawable.spartan_green, 0, 0);

        pieces.add(piece);
    }

    public void onDraw(Canvas canvas, int MarginX, int MarginY,int BoardSize, float Scale ) {
        for( Piece piece : pieces)
        {
            piece.onDraw(canvas,MarginX,MarginY,BoardSize,Scale);
        }
    }
}

