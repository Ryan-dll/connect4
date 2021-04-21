package edu.msu.weath151.connect4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import edu.msu.weath151.connect4.Cloud.Cloud;

import static edu.msu.weath151.connect4.MainActivity.NAME1;
import static edu.msu.weath151.connect4.MainActivity.NAME2;

public class AccountDlg extends DialogFragment{

    AlertDialog dlg = null;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Pass null as the parent view because its going in the dialog layout
        @SuppressLint("InflateParams")
        final View view = inflater.inflate(R.layout.account_create, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.makeAccount, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                final String username = ((EditText) view.findViewById(R.id.editTextUsername))
                        .getText().toString();
                final String password = ((EditText) view.findViewById(R.id.editTextPassword))
                        .getText().toString();

                final Cloud cloud = new Cloud(username, password);

                Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            boolean result = cloud.makeAccount();

                            dlg.cancel();
                            ArrayList<String> list = new ArrayList<>();
                            list.add(username);
                            list.add(password);
                            listener.onMakeAccountFinished(list, result);
                        }
                    }
                );
                thread.start();
            }
        });

        // Add a cancel button
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Cancel just closes the dialog box
            }
        });

        dlg = builder.create();

        return dlg;
    }

    private ListenerAccountDlg listener = null;

    void setMakeAccountListener(ListenerAccountDlg listener)
    {
        this.listener = listener;
    }
}
