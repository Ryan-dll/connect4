package edu.msu.weath151.connect4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListenerAccountDlg{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String USERNAME = "name1_gameplay";
    public static final String PASSWORD = "name2_gameplay";

    boolean loginResult = false;


    public void onStartGame(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(USERNAME, getNameOne());
        intent.putExtra(PASSWORD, getNameTwo());
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
            intent.putExtra(USERNAME, usernamePassword.get(0));
            intent.putExtra(PASSWORD, usernamePassword.get(1));

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
        String nameOne = getNameOne();
        String nameTwo = getNameTwo();
        final Cloud cloud = new Cloud(nameOne, nameTwo);

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                loginResult = cloud.loginAccount();
            }
        }
        );
        thread.start();

        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (loginResult == true){
            Intent intent = new Intent(this, JoinGameActivity.class);
            intent.putExtra(NAME1, nameOne);
            intent.putExtra(NAME2, nameTwo);
            startActivity(intent);
        }
        else {
            view.post(new Runnable() {
                @Override
                public void run() {
                    String string = null;
                    string = getString(R.string.LoginToast);
                    Toast.makeText(view.getContext(), string, Toast.LENGTH_LONG).show();
                }
            });
        }

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