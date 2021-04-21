package edu.msu.weath151.connect4;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AccountLoadingDlg extends DialogFragment {
    private ListenerAccountLoadingDlg listener = null;

    public void setListener(ListenerAccountLoadingDlg listener)
    {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.loading);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onLoadingDlgCancel();
            }
        });

        return builder.create();
    }
}
