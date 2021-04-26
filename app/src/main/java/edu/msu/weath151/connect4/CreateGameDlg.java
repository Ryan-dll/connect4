package edu.msu.weath151.connect4;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import edu.msu.weath151.connect4.Cloud.Cloud;

public class CreateGameDlg extends DialogFragment implements ListenerGameCreateDlg{

    volatile boolean threadContinue = true;

    private String username = "";
    private String password = "";
    private ListenerGameCreateDlg listener = null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setListener(ListenerGameCreateDlg listener) {
        this.listener = listener;
    }

    private AlertDialog dlg = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.creating_game);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                threadContinue = false;
            }
        });

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                cloud.setOnGameCreate(CreateGameDlg.this);
                boolean result = cloud.createGame(username, password);
                dlg.cancel();
            }
        });
        dlg = builder.create();
        thread.start();

        return dlg;
    }

    @Override
    public void onFinished(int game, int user) {
        if(threadContinue)
        {
            listener.onFinished(game, user);
        }
    }
}
