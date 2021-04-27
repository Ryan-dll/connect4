package edu.msu.weath151.connect4;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import edu.msu.weath151.connect4.Cloud.Cloud;

import static edu.msu.weath151.connect4.JoinGameActivity.GAMEID;
import static edu.msu.weath151.connect4.JoinGameActivity.USERID;

public class JoiningDlg extends DialogFragment {

    private String catId;
    private String USER = "";
    private String PASS = "";

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    /**
     * Set true if we want to cancel
     */
    private volatile boolean cancel = false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ID,catId);
    }

    private final static String ID = "id";

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancel = true;
    }

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        if(bundle != null) {
            catId = bundle.getString(ID);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        cancel = false;

        // Set the title
        builder.setTitle(R.string.loading_game);

        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        cancel = true;
                    }
                });


        // Create the dialog box
        final AlertDialog dlg = builder.create();


        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud(USER,PASS);
                String result = cloud.joinGame(catId);

                if(cancel) {
                    return;
                }

                if (result != null){
                    Intent intent = new Intent(getActivity(), GamePlayActivity.class);
                    intent.putExtra(USERID, result);
                    intent.putExtra(GAMEID, Integer.parseInt(catId));
                    intent.putExtra(JoinGameActivity.USERNAME, USER);
                    intent.putExtra(JoinGameActivity.PASSWORD, PASS);
                    intent.putExtra(JoinGameActivity.CREATEDGAME, false);

                    final Intent finalIntent = intent;

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            startActivity(finalIntent);
                        }
                    });

                }
                else {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getActivity(),
                                    R.string.join_fail_toast,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        }).start();

        return dlg;
    }

}
