package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameEndActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        Intent intent = getIntent();
        String player1_name = intent.getStringExtra(GamePlayActivity.PLAYER1_NAME);
        String player2_name = intent.getStringExtra(GamePlayActivity.PLAYER2_NAME);
        TextView player1 = (TextView)findViewById(R.id.winner);
        player1.setText(player1_name);

        // TO DO:
        //Use android resources instead of Literal String concatenation
        if(!intent.getBooleanExtra(GamePlayActivity.PLAYER_TURN, false))
        {
            ((TextView)findViewById(R.id.winner)).setText(String.format("%s beat %s!", player1_name, player2_name));
        }
        else if(intent.getBooleanExtra(GamePlayActivity.PLAYER_TURN, true)) {
            ((TextView)findViewById(R.id.winner)).setText(String.format("%s beat %s!", player2_name, player1_name));
        }

        else if(intent.getBooleanExtra(GamePlayActivity.WINNER, false)) {
            ((TextView)findViewById(R.id.winner)).setText(String.format("%s beat %s!", player1_name, player2_name));
        }
        else if(!intent.getBooleanExtra(GamePlayActivity.WINNER, true)) {
            ((TextView)findViewById(R.id.winner)).setText(String.format("%s beat %s!", player2_name, player1_name));
        }


    }



    public void onPlayGame(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        startActivity(intent);
    }


}