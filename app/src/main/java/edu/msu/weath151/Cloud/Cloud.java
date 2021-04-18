package edu.msu.weath151.Cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.msu.weath151.connect4.R;

public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~shorery1/cse476/Project2/";
    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private String USER = "";
    private String PASSWORD = "";


    /**
     * An adapter so that list boxes can display a list of games from
     * the cloud server.
     */
    public static class CatalogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
            }

            TextView tv = (TextView)view.findViewById(R.id.Host);


            return view;
        }
    }

}
