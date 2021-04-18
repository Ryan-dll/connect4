package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class JoinGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void onJoinGame(View view) {
        LobbyDlg dlg2 = new LobbyDlg();
        dlg2.show(getSupportFragmentManager(), "joinGame");
    }
}