package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GamePlayActivity extends AppCompatActivity {



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
        startActivity(intent);
    }

    public void greenPiece(View view) {
    }


    public void whitePiece(View view) {
        //((GameplayView) view).addPiece(this, R.id.greenPiece);
        //((GameplayView) view).invalidate();
    }

    private GameplayView getView()
    {
        return (GameplayView) findViewById(R.id.viewGame);
    }



}