package edu.msu.weath151.connect4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Custom view class for Connect-4 board
 */
public class GameplayView extends View {

    /**
     * Paint object for drawing
     */
    private Paint paint;


    private Piece piece;


    /**
     * The actual board
     */
    private Board board;

    public GameplayView(Context context) {
        super(context);
        init(null, 0);

    }

    public GameplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameplayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        board = new Board(getContext());
        piece = new Piece(getContext(), R.drawable.spartan_green, 0, 0);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //
        // Get an X y location
        //

        float relX = event.getX();
        float relY = event.getY();

        // Handle the touch event

        switch(event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                piece.setLocation(relX, relY);
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_MOVE:
                piece.setLocation(relX, relY);
                break;
        }

        invalidate();
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        board.onDraw(canvas);
        piece.onDraw(canvas);

    }




}