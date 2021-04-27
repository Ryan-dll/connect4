package edu.msu.weath151.connect4.Cloud;

import edu.msu.weath151.connect4.Cloud.Models.GameCreate;
import edu.msu.weath151.connect4.Cloud.Models.GameState;
import edu.msu.weath151.connect4.Cloud.Models.GamesCatalog;
import edu.msu.weath151.connect4.Cloud.Models.Result;
import edu.msu.weath151.connect4.Cloud.Models.MadeMove;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.weath151.connect4.Cloud.Cloud.DISPLAY_GAME_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.GET_MOVE_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.JOIN_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.LOGIN_ACCOUNT_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_ACCOUNT_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_GAME_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_MOVE_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.REQUEST_GAME_PATH;

public interface ConnectService {
    @FormUrlEncoded
    @POST(MAKE_ACCOUNT_PATH)
    Call<Result> makeAccount(
            @Field("username") String username,
            @Field("magic") String magic,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(MAKE_GAME_PATH)
    Call<GameCreate> makeGame(
            @Field("user") String user,
            @Field("magic") String magic,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST(LOGIN_ACCOUNT_PATH)
    Call<Result> loginAccount(
            @Field("username") String username,
            @Field("magic") String magic,
            @Field("password") String password
    );

    @GET(DISPLAY_GAME_PATH)
    Call<GamesCatalog> getCatalog(
            @Query("magic") String magic,
            @Query("username") String username,
            @Query("password") String password

    );

    @FormUrlEncoded
    @POST(JOIN_PATH)
    Call<Result> joinGame(
            @Field("username") String username,
            @Field("magic") String magic,
            @Field("password") String password,
            @Field("gameid") String gameid
    );

    @FormUrlEncoded
    @POST(MAKE_MOVE_PATH)
    Call<MadeMove> makeMove(
            @Field("user") String user,
            @Field("magic") String magic,
            @Field("pass") String pass,
            @Field("gameid") int gameId,
            @Field("player") String player,
            @Field("move") int move
    );

    @GET(GET_MOVE_PATH)
    Call<MadeMove> getMove(
            @Query("user") String user,
            @Query("magic") String magic,
            @Query("pass") String pass,
            @Query("gameid") int gameId,
            @Query("player") String player
    );

    @GET(REQUEST_GAME_PATH)
    Call<GameState> requestGamestate(
            @Query("magic") String magic,
            @Query("gameid") String gameid
    );
}
