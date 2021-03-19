package edu.msu.weath151.connect4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static final String NAME1 = "name1";
    public static final String NAME2 = "name2";

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        EditText editText1 = findViewById(R.id.EditText1);
        EditText editText2 = findViewById(R.id.EditText2);
        intent.putExtra(NAME1, editText1.getText().toString());
        intent.putExtra(NAME2, editText2.getText().toString());
        startActivity(intent);
    }

}