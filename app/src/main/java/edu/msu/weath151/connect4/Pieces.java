package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Pieces {

    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    public Pieces()
    {
        init();
    }

    public Pieces(Context context)
    {
        Piece piece = new Piece(context, R.drawable.spartan_green, 0, 0);

        pieces.add(piece);
    }

    public void onDraw(Canvas canvas) {
        for( Piece piece : pieces)
        {
            piece.onDraw(canvas);
        }
    }

    public void init()
    {
        Piece piece = new Piece();
        pieces.add(piece);
    }

}

