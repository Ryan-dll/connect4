package edu.msu.weath151.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.msu.weath151.connect4.Cloud.Cloud;

import static edu.msu.weath151.connect4.JoinGameActivity.GAMEID;

public class GamePlayActivity extends AppCompatActivity {

    public static final String PLAYER_TURN = "player_turn";
    public static final String WINNER= "player_turn";
    public static final String PLAYER1_NAME = "Player1";
    public static final String PLAYER2_NAME = "Player2";
    public static final String TURN = "turn";
    private String GAME_ID = "";

    String player1_Name;
    String player2_Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();

        GAME_ID = intent.getStringExtra(GAMEID);
        /*
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

    public void onRequestGameState(View view){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                String result = cloud.grabGamestate(GAME_ID);


                if (result != null){
                    getView().getBoard().updateGame(result);
                }
                else {
/*                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    this,
                                    R.string.join_fail_toast,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/
                }
            }
        }).start();
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

                // Reset game here
                /////////////////


                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        new CountDownTimer(15000, 5000)
                        {
                            @Override
                            public void onFinish() {
                                // game simply times out
                            }

                            @Override
                            public void onTick(long millisUntilFinished) {
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run() {
                                        String newGame = new Cloud()
                                                .getMove(intent.getIntExtra(JoinGameActivity.GAMEID, 0),
                                                        intent.getStringExtra(JoinGameActivity.USERNAME),
                                                        intent.getStringExtra(JoinGameActivity.PASSWORD),
                                                        "2");
                                    }
                                }).start();
                            }
                        }.start();
                    }
                });
            }
        }).start();
    }

    public static final String PLACED_PIECES = "placed_pieces";
}