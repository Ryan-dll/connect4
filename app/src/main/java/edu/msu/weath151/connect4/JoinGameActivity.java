package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class JoinGameActivity extends AppCompatActivity implements ListenerGameCreateDlg{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void onJoinGame(View view) {
        LobbyDlg dlg2 = new LobbyDlg();
        dlg2.show(getSupportFragmentManager(), "joinGame");
    }

    public void onCreateGame(View view)
    {
        CreateGameDlg dlg = new CreateGameDlg();
        dlg.setUsername(getIntent().getStringExtra(MainActivity.USERNAME));
        dlg.setPassword(getIntent().getStringExtra(MainActivity.PASSWORD));
        dlg.setListener(this);
        dlg.show(getSupportFragmentManager(), "createGame");
    }

    public static String USERID = "userid";
    public static String GAMEID = "gameid";

    @Override
    public void onFinished(int game, int user) {
        Intent intent = new Intent(getApplicationContext(), GamePlayActivity.class);
        intent.putExtra(USERID, user);
        intent.putExtra(GAMEID, game);

        final Intent finalIntent = intent;

        runOnUiThread(new Runnable()
        {
            @Override
            public void run() {
                startActivity(finalIntent);
            }
        });
    }
}