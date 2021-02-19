package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Canvas;

public class Board {

    private Pieces pieces = null;

    public Board()
    {
        init();
    }

    public Board(Context context){
        pieces = new Pieces(context);
    }

    public void onDraw(Canvas canvas){
        pieces.onDraw(canvas);
    }

    public void init()
    {
        pieces = new Pieces();
    }
}
