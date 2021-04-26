package edu.msu.weath151.connect4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
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

    private float boardSize;

    private View boardView;

    private float scaleFactor;

    private Piece emptyPiece;

    public static final String WINNER= "player_turn";
    public static final String PLAYER1_NAME = "Player1";
    public static final String PLAYER2_NAME = "Player2";

    private boolean player1 = false;
    private boolean player2 = false;
    private boolean winner;
    public boolean getWinner(){return winner;}

    private String Player1Name;
    private String Player2Name;

    public void setNames(String p1, String p2){
        Player1Name = p1;
        Player2Name = p2;
    }

    private boolean Player1Turn = true;

    public boolean isPlayer1Turn() {
        return Player1Turn;
    }

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private Piece dragging = null;

    public Piece getDragging() {
        return dragging;
    }

    public int getMove()
    {
        if (dragging != null)
        {
            return dragging.getPositionSlot();
        }
        return -1;
    }

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

    private Pieces pieces = new Pieces();
    private Piece piece;
    private ArrayList<ArrayList<BoardGrid>> board_pieces = new ArrayList<ArrayList<BoardGrid>>();

    private BoardGrid Grid;

    private boolean FirstDraw = true;

    private Canvas canvas;

    private boolean touchpiece;
    int num;
    private boolean colorPiece;

    public Board(Context context, View view) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);
        pieces = new Pieces(context);
        this.boardView = view;
        emptyPiece = new Piece(context, R.drawable.empty_space, 0, 0);
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

    public float getBoardSize() {
        return boardSize;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public ArrayList<ArrayList<BoardGrid>> getBoard_pieces() {
        return board_pieces;
    }

    public void AssembleBoard(Context context) {
        if (FirstDraw){
            for (int i = 0; i < 7; i++) {
                ArrayList<BoardGrid> column = new ArrayList<BoardGrid>();
                BoardGrid.setSlot(context);
                for (int j = 0; j < 6; j++) {
                    BoardGrid BoardPiece = new BoardGrid((i/6f), (j/6f));
                    column.add(BoardPiece);
                }
                board_pieces.add(column);
            }
        }
    }

    public void onDraw(Canvas canvas){
        float wid = canvas.getWidth();
        float hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        float minDim = wid < hit ? wid : hit;

        boardSize = minDim;

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

        for( int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 6; j++){
                board_pieces.get(i).get(j).onDraw(canvas, marginX, marginY, boardSize, scaleFactor);
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

        float relX = (event.getX() - marginX - (float)60) / (boardSize) ;
        float relY = (event.getY() - marginY - (float)60) / (boardSize) ;
        touchpiece = true;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                onTouched(relX, relY);
                //pieces.setLocation(relX, relY);
                if (dragging == null) {
                    if (Player1Turn) {
                        greenColor(relX, relY);
                        player1 = true;
                        player2 = false;
                    } else {
                        whiteColor(relX, relY);

                        player2 = true;
                        player1 = false;
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                if (dragging != null) {
                    SnapPiece(relX, relY);
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;

                    return true;
                }
                return true;
        }

        return false;
    }

    public void SnapPiece(float relX, float relY){
        float min = 1000f;
        float minX = 0;
        float minY = 0;
        int row = 0;
        int column = 0;
        for( ArrayList<BoardGrid> empty_pieces : board_pieces)
        {
            for (BoardGrid empty_piece : empty_pieces){
                int wid = BoardGrid.getSlot().getWidth();

                float BoardPieceX = empty_piece.getX() ;
                float BoardPieceY = empty_piece.getY() ;

                float PieceX = dragging.getX();
                float PieceY = dragging.getY();
                float x = wid/((2f*boardSize));

                float Distance = (float)Math.sqrt(Math.pow(Math.abs(PieceX - BoardPieceX), 2) +
                        Math.pow(Math.abs(PieceY - BoardPieceY), 2));
                if(Distance < x && Distance < min) {

                    min = Distance;
                    minX = BoardPieceX;
                    minY = BoardPieceY;

                    // Calculate row and column for isValid()
                    float bX = BoardPieceX/x;
                    float bY = BoardPieceY/x;
                    row = (int)Math.ceil(bX);
                    column = (int)Math.ceil(bY);
                }
            }
        }
        if ((1000f - min) > 500f)
        {
            dragging.setLocation(minX, minY);
            boolean ok = isValid(row,column);
            if (ok){
                Player1Turn = !Player1Turn;
            }
        }
    }

    public boolean isValid(int row, int column) {
        if (dragging != null){

            for (int i = 5; i >= column; i--) {
                BoardGrid piece = board_pieces.get(row).get(i);
                if (piece.isTaken() == null) {
                    //piece.setTaken(dragging);
                    dropPiece(row, i);
                    return true;
                }
            }
            pieces.remove(dragging);
            dragging = null;
            return false;

        }
        return false;
    }

    public void dropPiece(int row, int column) {
        BoardGrid piece = board_pieces.get(row).get(column);
        dragging.setLocation(piece.getX(), piece.getY());
        boolean win = checkWin(row,column);
        if (win) {
            Intent intent = new Intent(context, GameEndActivity.class);
            intent.putExtra(WINNER, !Player1Turn);
            intent.putExtra(PLAYER1_NAME, Player1Name);
            intent.putExtra(PLAYER2_NAME, Player2Name);
            context.startActivity(intent);
        }
    }

    public boolean checkWin(int row, int column) {
        if (verticalWin(row,column) || horizontalWin(row, column) ||
            diagonalWin(row, column) || reverseDiagonalWin(row, column)) {
            if(player1)
            {
                winner = false;
            }
            else{
                winner = true;
            }
            return true;
        }
        if(player1)
        {
            winner = false;
        }
        else{
            winner = true;
        }
        return false;
    }

    public boolean verticalWin(int row, int column) {
        int count = 0;
        for (int i=0;i<6;i++) {
            if (board_pieces.get(row).get(i).isTaken() != null){
                if (board_pieces.get(row).get(i).isTaken().isPlayer1() == Player1Turn) {
                    count++;
                } else {
                    count = 0;
                }
                if (count>=4) {
                    if(player1)
                    {
                        winner = false;
                    }
                    else{
                        winner = true;
                    }
                    return true;
                }
            }
        }
        if(player1)
        {
            winner = false;
        }
        else{
            winner = true;
        }
        return false;
    }

    public boolean horizontalWin(int row, int column) {
        int count = 0;
        for (int i=0;i<7;i++) {
            if (board_pieces.get(i).get(column).isTaken() != null){
                if (board_pieces.get(i).get(column).isTaken().isPlayer1() == Player1Turn) {
                    count++;
                } else {
                    count = 0;
                }
                if (count>=4) {
                    if(player1)
                    {
                        winner = false;
                    }
                    else{
                        winner = true;
                    }
                    return true;
                }
            }
        }
        if(player1)
        {
            winner = false;
        }
        else{
            winner = true;
        }
        return false;
    }
    public boolean diagonalWin(int row, int column) {
        for (int i=3; i<7; i++){
            for (int j=0; j< 3; j++) {
                if (board_pieces.get(i).get(j).isTaken() != null &&
                        board_pieces.get(i - 1).get(j + 1).isTaken() != null &&
                        board_pieces.get(i - 2).get(j + 2).isTaken() != null &&
                        board_pieces.get(i - 3).get(j + 3).isTaken() != null) {
                    if (board_pieces.get(i).get(j).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 1).get(j + 1).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 2).get(j + 2).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 3).get(j + 3).isTaken().isPlayer1() == Player1Turn) {
                        if(player1)
                        {
                            winner = false;
                        }
                        else{
                            winner = true;
                        }
                        return true;
                    }
                }
            }
        }
        if(player1)
        {
            winner = false;
        }
        else{
            winner = true;
        }
        return false;
    }
    public boolean reverseDiagonalWin(int row, int column) {
        for (int i=3; i<7; i++){
            for (int j=3; j< 6; j++) {
                if (board_pieces.get(i).get(j).isTaken() != null &&
                        board_pieces.get(i - 1).get(j - 1).isTaken() != null &&
                        board_pieces.get(i - 2).get(j - 2).isTaken() != null &&
                        board_pieces.get(i - 3).get(j - 3).isTaken() != null) {
                    if (board_pieces.get(i).get(j).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 1).get(j - 1).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 2).get(j - 2).isTaken().isPlayer1() == Player1Turn &&
                            board_pieces.get(i - 3).get(j - 3).isTaken().isPlayer1() == Player1Turn) {
                        if(player1)
                        {
                            winner = false;
                        }
                        else{
                            winner = true;
                        }
                        return true;
                    }
                }
            }
        }
        if(player1)
        {
            winner = false;
        }
        else{
            winner = true;
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


        /*
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

         */
        if (dragging != null)
        {
            lastRelX = x;
            lastRelY = y;
        }
        return true;
    }

    public void greenColor(float x, float y){
        Piece piece = new Piece(this.context, R.drawable.spartan_green, x, y);
        piece.setPlayer1(Player1Turn);
        pieces.addPiece(piece);
        dragging = piece;
        lastRelX = x;
        lastRelY = y;
        colorPiece = true;
    }

    public void whiteColor(float x, float y){
        Piece piece = new Piece(this.context, R.drawable.spartan_white, x, y);
        piece.setPlayer1(Player1Turn);
        pieces.addPiece(piece);
        dragging = piece;
        lastRelX = x;
        lastRelY = y;
        colorPiece = false;
    }

    public void addPiece(Context context, int id){pieces.addNewPiece(context, id);}

    public void onSaveInstanceState(Bundle outState)
    {
        ArrayList<Integer> placedPieces = new ArrayList<>();
        for(int i = 0; i < 42; i++)
        {
            placedPieces.add(0);
        }

        for(Piece piece : pieces.getPieces())
        {
            int i = 0;
            int j = 0;
            float x = piece.getX()*6;
            float y = piece.getY()*6;
            i = Math.round(y);
            float est = (float)i - y;
            if(!(est < 0.01f && est > -0.01f))
            {
                continue;
            }
            j = Math.round(x);
            est = (float)j - x;
            if(!(est < 0.01f && est > -0.01f))
            {
                continue;
            }
            if(piece.isGreen())
            {
                placedPieces.set(i*7 + j,1);
            }
            else
            {
                placedPieces.set(i*7 + j,2);
            }
        }

        outState.putIntegerArrayList(GamePlayActivity.PLACED_PIECES, placedPieces);
        outState.putBoolean(GamePlayActivity.TURN, Player1Turn);
    }

    public void onRestoreState(Context context,Bundle savedInstanceState)
    {
        if(savedInstanceState.containsKey(GamePlayActivity.TURN))
        {
            Player1Turn = savedInstanceState.getBoolean(GamePlayActivity.TURN);
        }

        if(!savedInstanceState.containsKey(GamePlayActivity.PLACED_PIECES))
        {
            return;
        }
        ArrayList<Integer> placedPieces = savedInstanceState.getIntegerArrayList(GamePlayActivity.PLACED_PIECES);



        if(placedPieces == null)
        {
            return;
        }


        for(int i = 0; i < placedPieces.size(); i++)
        {
            pieces.setLocation((float)(i%7)/6,(float)(i/7)/6);
            switch(placedPieces.get(i))
            {
                case 0:
                    break;
                case 1:
                    pieces.addNewPiece(context, R.drawable.spartan_green);
                    break;
                case 2:
                    pieces.addNewPiece(context, R.drawable.spartan_white);
                    break;
            }
        }
    }
}
