package edu.msu.weath151.connect4.Cloud;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.msu.weath151.connect4.Cloud.Models.ActiveGame;
import edu.msu.weath151.connect4.Cloud.Models.GameCreate;
import edu.msu.weath151.connect4.Cloud.Models.GamesCatalog;
import edu.msu.weath151.connect4.Cloud.Models.Result;
import edu.msu.weath151.connect4.ListenerGameCreateDlg;
import edu.msu.weath151.connect4.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~weath151/cse476/Project2/";
    private static final String MAGIC = "NechAtHa6RuzeR8x";
    public static final String MAKE_ACCOUNT_PATH = "user-create.php";
    public static final String MAKE_GAME_PATH = "game-create.php";
    public static final String LOGIN_ACCOUNT_PATH = "login-attempt.php";
    public static final String DISPLAY_GAME_PATH = "game-active-catalog.php";
    public static final String JOIN_PATH = "game-join.php";

    private String USER = "";
    private String PASSWORD = "";
    private ListenerGameCreateDlg onGameCreate = null;

    public void setOnGameCreate(ListenerGameCreateDlg onGameCreate) {
        this.onGameCreate = onGameCreate;
    }

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

    public String joinGame(String GAMEID){
        if(USER == null)
        {
            return null;
        }

        ConnectService service = retrofit.create(ConnectService.class);

        try
        {
            Response<Result> response = service.joinGame(USER, MAGIC, PASSWORD, GAMEID).execute();

            if(!response.isSuccessful())
            {
                return null;
            }

            Result result = response.body();

            if(result.getStatus().equals("yes"))
            {
                return USER;
            }
            else
            {
                return null;
            }
        }
        catch(IOException e)
        {
            return null;
        }
        catch(Exception e)
        {
            return null;
        }

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

    public boolean loginAccount()
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
            Response<Result> response = service.loginAccount(USER, MAGIC, PASSWORD).execute();

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


    public boolean createGame(String username, String password)
    {
        ConnectService service = retrofit.create(ConnectService.class);
        try
        {
            Response<GameCreate> response = service.makeGame(username, MAGIC, password).execute();

            if(!response.isSuccessful())
            {
                return false;
            }

            GameCreate body = response.body();

            if(!body.getStatus().equals("yes"))
            {
                return false;
            }

            int gameid = body.getGame();

            int userid = body.getUser();

            String status = body.getStatus();

            onGameCreate.onFinished(gameid, userid);

        }
        catch(IOException e)
        {
            return false;
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    /**
     * An adapter so that list boxes can display a list of games from
     * the cloud server.
     */
    public static class CatalogAdapter extends BaseAdapter {


        /**
         * The items we display in the list box. Initially this is
         * null until we get items from the server.
         */
        private GamesCatalog catalog = new GamesCatalog("", new ArrayList<ActiveGame>(), "");

        /**
         * Constructor
         */
        public CatalogAdapter(final View view) {
            // Create a thread to load the catalog
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        catalog = getCatalog();

                        if (catalog.getStatus().equals("no")) {
                            String msg = "Loading catalog returned status 'no'! Message is = '" + catalog.getMessage() + "'";
                            throw new Exception(msg);
                        }

                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }

                        });
                    } catch (Exception e) {
                        Log.e("CatalogAdapter", "Something went wrong when loading the catalog", e);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(), R.string.catalog_fail, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }


        public GamesCatalog getCatalog() throws IOException {
            ConnectService service = retrofit.create(ConnectService.class);
            Response<GamesCatalog> response = service.getCatalog(MAGIC).execute();
            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("getCatalog", "Failed to get catalog, response code is = " + response.code());
                return null;
            }
            GamesCatalog catalog = response.body();
            if (catalog.getStatus().equals("no")) {
                Log.e("getCatalog", "Failed to get catalog, msg is = " + catalog.getMessage());
                return null;
            };
            return catalog;
        }

        @Override
        public int getCount() {
            return catalog.getItems().size();
        }

        @Override
        public ActiveGame getItem(int position) {
            return catalog.getItems().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lobby_item, parent, false);
            }

            TextView tv = (TextView)view.findViewById(R.id.Host);
            tv.setText(catalog.getItems().get(position).getUser());

            return view;
        }

        public String getId(int position) {
            return catalog.getItems().get(position).getId();
        }
        public String getName(int position) {
            return catalog.getItems().get(position).getUser();
        }
    }

}
