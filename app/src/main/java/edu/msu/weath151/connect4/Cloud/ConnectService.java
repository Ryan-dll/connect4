package edu.msu.weath151.connect4.Cloud;

import edu.msu.weath151.connect4.Cloud.Models.Account;
import edu.msu.weath151.connect4.Cloud.Models.GameCreate;
import edu.msu.weath151.connect4.Cloud.Models.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static edu.msu.weath151.connect4.Cloud.Cloud.LOGIN_ACCOUNT_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_ACCOUNT_PATH;
import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_GAME_PATH;

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
}
