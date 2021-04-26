package edu.msu.weath151.connect4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import edu.msu.weath151.connect4.Cloud.Cloud;

public class AccountDlg extends DialogFragment implements ListenerAccountLoadingDlg {

    private AlertDialog dlg = null;

    private volatile boolean threadContinue = true;

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

                dlg.cancel();

                Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            boolean result = cloud.makeAccount();
                            if(threadContinue)
                            {
                                ArrayList<String> list = new ArrayList<>();
                                list.add(username);
                                list.add(password);
                                listener.onMakeAccountFinished(list, result);
                            }
                        }
                    }
                );
                thread.start();

                AccountLoadingDlg dlg2 = new AccountLoadingDlg();
                dlg2.setListener(AccountDlg.this);
                dlg2.show(getFragmentManager(), "accountloading");

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

    @Override
    public void onLoadingDlgCancel()
    {
        threadContinue = false;
    }

    private ListenerAccountDlg listener = null;

    void setMakeAccountListener(ListenerAccountDlg listener)
    {
        this.listener = listener;
    }
}
