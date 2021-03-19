package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GamePlayActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);


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