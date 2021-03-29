package edu.msu.weath151.connect4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity {

    public static final String PLAYER_TURN = "player_turn";
    public static final String PLAYER1_NAME = "player1_name";
    public static final String PLAYER2_NAME = "player2_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();
        String player1Name = intent.getStringExtra(MainActivity.NAME1);
        String player2Name = intent.getStringExtra(MainActivity.NAME2);
        TextView player1 = (TextView)findViewById(R.id.textView);
        TextView player2 = (TextView)findViewById(R.id.textView2);
        player1.setText(player1Name);
        player2.setText(player2Name);
    }

    public void onEndGame(View view) {
        Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra(PLAYER_TURN, getView().getBoard().isPlayer1Turn());
        // TO DO:
        // Replace each second input with the real player names
        intent.putExtra(PLAYER1_NAME, PLAYER1_NAME);
        intent.putExtra(PLAYER2_NAME, PLAYER2_NAME);
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

    public static final String PLACED_PIECES = "placed_pieces";
}