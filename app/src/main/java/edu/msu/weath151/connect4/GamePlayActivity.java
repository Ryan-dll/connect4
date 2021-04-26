package edu.msu.weath151.connect4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.msu.weath151.connect4.Cloud.Cloud;

public class GamePlayActivity extends AppCompatActivity {

    public static final String PLAYER_TURN = "player_turn";
    public static final String WINNER= "player_turn";
    public static final String PLAYER1_NAME = "Player1";
    public static final String PLAYER2_NAME = "Player2";
    public static final String TURN = "turn";

    String player1_Name;
    String player2_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        /*
        Intent intent = getIntent();

        String player1Name = intent.getStringExtra(MainActivity.USERNAME);
        String player2Name = intent.getStringExtra(MainActivity.PASSWORD);
        player1_Name = intent.getStringExtra(MainActivity.USERNAME);
        player2_Name = intent.getStringExtra(MainActivity.PASSWORD);
        TextView player1 = (TextView)findViewById(R.id.textView);
        TextView player2 = (TextView)findViewById(R.id.textView2);
        getView().getBoard().setNames(player1Name,player2Name);
        if(player1Name.contentEquals(""))
        {
            player1Name = getString(R.string.Player1DefaultName);
        }
        if(player2Name.contentEquals(""))
        {
            player2Name = getString(R.string.Player2DefaultName);
        }
        player1.setText(player1Name);
        player2.setText(player2Name);

         */

        if(savedInstanceState != null)
        {
            getView().getBoard().onRestoreState(this ,savedInstanceState);
        }
    }


    public void onEndGame(View view) {

        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra(PLAYER_TURN, getView().getBoard().isPlayer1Turn());
        intent.putExtra(PLAYER1_NAME,player1_Name);
        intent.putExtra(PLAYER2_NAME, player2_Name);
        startActivity(intent);
    }

    public void onEndGameDone(View view) {

        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra(WINNER, getView().getBoard().getWinner());
        // TO DO:
        // Replace each second input with the real player names
        intent.putExtra(PLAYER1_NAME,player1_Name);
        intent.putExtra(PLAYER2_NAME, player2_Name);
        startActivity(intent);
    }


    private GameplayView getView()
    {
        return (GameplayView) findViewById(R.id.viewGame);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getView().getBoard().onSaveInstanceState(outState);
    }

    public void makeMove(View view)
    {
        GameplayView gamePlayView = getView();
        final Intent intent = getIntent();
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                // Temp set whichUser to 1
                String newGameState = new Cloud()
                        .makeMove(intent.getIntExtra(JoinGameActivity.GAMEID, 0),
                                intent.getStringExtra(JoinGameActivity.USERNAME),
                                intent.getStringExtra(JoinGameActivity.PASSWORD),
                                gamePlayView.getMove(),"1");
            }
        }).start();
    }

    public static final String PLACED_PIECES = "placed_pieces";
}