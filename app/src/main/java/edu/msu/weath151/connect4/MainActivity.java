package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.msu.weath151.connect4.Cloud.Cloud;

public class MainActivity extends AppCompatActivity {


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
        String nameOne = getNameOne();
        String nameTwo = getNameTwo();
        final Cloud cloud = new Cloud(nameOne, nameTwo);

        Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run() {
                    cloud.makeAccount();
                }
            }
        );
        thread.start();

        Intent intent = new Intent(this, JoinGameActivity.class);
        intent.putExtra(NAME1, nameOne);
        intent.putExtra(NAME2, nameTwo);
        startActivity(intent);
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