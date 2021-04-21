package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.msu.weath151.connect4.Cloud.Cloud;

public class MainActivity extends AppCompatActivity implements ListenerAccountDlg{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String NAME1 = "name1_gameplay";
    public static final String NAME2 = "name2_gameplay";


    public void onStartGame(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(NAME1, getNameOne());
        intent.putExtra(NAME2, getNameTwo());
        startActivity(intent);
    }

    public void onStartLobby(View view) {
        Intent intent = new Intent(this, JoinGameActivity.class);
        startActivity(intent);
    }

    public void onMakeAccount(View view)
    {
        AccountDlg dlg = new AccountDlg();
        dlg.setMakeAccountListener(this);
        dlg.show(getSupportFragmentManager(), "account");
    }

    @Override
    public void onMakeAccountFinished(final ArrayList<String> usernamePassword, boolean result) {
        if (result){

            Intent intent = new Intent(this, JoinGameActivity.class);
            intent.putExtra(NAME1, usernamePassword.get(0));
            intent.putExtra(NAME2, usernamePassword.get(1));

            final Intent realIntent = intent;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(realIntent);
                }
            });
        }
        else
        {
            final MainActivity context = this;
            runOnUiThread(new Runnable()
            {
                @Override
                public void run() {
                    Toast.makeText(
                            context,
                            R.string.account_fail,
                            Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    }

    public void onMakeAccountFinished()
    {

    }

    public void onLogin(View view) {
        LoginDlg dlg2 = new LoginDlg();
        dlg2.show(getSupportFragmentManager(), "load");
    }

    private String getNameOne()
    {
        EditText editText = findViewById(R.id.EditText1);
        return editText.getText().toString();
    }

    private String getNameTwo()
    {
        EditText editText = findViewById(R.id.EditText2);
        return editText.getText().toString();
    }
}