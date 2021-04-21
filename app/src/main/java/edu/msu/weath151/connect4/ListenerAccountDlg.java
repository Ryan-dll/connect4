package edu.msu.weath151.connect4;

import java.util.ArrayList;

public interface ListenerAccountDlg {
    void onMakeAccountFinished(ArrayList<String> usernamePassword, boolean result);
}
