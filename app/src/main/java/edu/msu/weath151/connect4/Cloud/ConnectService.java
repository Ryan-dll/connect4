package edu.msu.weath151.connect4.Cloud;

import edu.msu.weath151.connect4.Cloud.Models.Account;
import edu.msu.weath151.connect4.Cloud.Models.Result;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static edu.msu.weath151.connect4.Cloud.Cloud.MAKE_ACCOUNT_PATH;

public interface ConnectService {
    @FormUrlEncoded
    @POST(MAKE_ACCOUNT_PATH)
    Call<Result> makeAccount(
            @Field("username") String username,
            @Field("magic") String magic,
            @Field("password") String password
    );
}
