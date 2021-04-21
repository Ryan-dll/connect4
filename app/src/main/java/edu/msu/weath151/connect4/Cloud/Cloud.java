package edu.msu.weath151.connect4.Cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;

import edu.msu.weath151.connect4.Cloud.Models.Result;
import edu.msu.weath151.connect4.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~weath151/cse476/Project2/";
    private static final String MAGIC = "NechAtHa6RuzeR8x";
    public static final String MAKE_ACCOUNT_PATH = "user-create.php";
    private String USER = "";
    private String PASSWORD = "";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public Cloud(){}

    public Cloud(String USER, String PASSWORD)
    {
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public boolean makeAccount()
    {
        if(USER == null)
        {
            return false;
        }
        if(PASSWORD == null)
        {
            return false;
        }

        ConnectService service = retrofit.create(ConnectService.class);

        try
        {
            Response<Result> response = service.makeAccount(USER, MAGIC, PASSWORD).execute();

            if(!response.isSuccessful())
            {
                return false;
            }

            Result result = response.body();

            if(result.getStatus().equals("yes"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(IOException e)
        {
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

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