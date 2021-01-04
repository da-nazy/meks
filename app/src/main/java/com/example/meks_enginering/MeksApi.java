package com.example.meks_enginering;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MeksApi {
    @GET("api/login/")
    Call<get_user> getUser(
            @Query("email") String email,
            @Query("p") String password,
            @Query("SECKEY") String secretKey
    );
}
