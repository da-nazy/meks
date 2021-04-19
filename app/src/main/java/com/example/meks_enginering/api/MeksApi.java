package com.example.meks_enginering.api;

import com.example.meks_enginering.api.get_user;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeksApi {
    @GET("api/login/")
    Call<get_user> getUser(
            @Query("email") String email,
            @Query("p") String password,
            @Query("SECKEY") String secretKey
    );

    // For customer account registration api
    @GET("api/customer/create")
    Call<customerAccountRegistration>customerRegistration(
          @Query("email") String email, @Query("password") String password, @Query("surname") String surname, @Query("SECKEY") String seckey
    );

    // for customer account verification next
    @GET("api/profile/")
    Call<getUserProfile> getUserProfile(@Query("userid") String userid, @Query("email") String email_address, @Query("SECKEY") String SECKEY);

    @GET("api/employee/update")
    Call<updateEmployee> updateEmployee(@Query("SECKEY") String str, @Query("surname") String str2, @Query("firstname") String str3, @Query("othername") String str4, @Query("gender") String str5, @Query("email") String str6, @Query("password") String str7, @Query("account_type") String str8, @Query("userid") String str9);

    @GET("api/employee/create")
    Call<createEmployee> addUser(@Query("SECKEY") String str, @Query("surname") String str2, @Query("firstname") String str3, @Query("othername") String str4, @Query("gender") String str5, @Query("email") String str6, @Query("password") String str7, @Query("account_type") String str8);

    @GET("api/employee/delete")
    Call<deleteEmployee> deleteEmployee(@Query("userid") String str, @Query("email") String str2, @Query("SECKEY") String str3);

    @GET("api/employee/list")
    Call<getEmployee> getEmployees(@Query("showlist") boolean z, @Query("SECKEY") String str);

    @GET("api/app/sub-category")
    Call<SubCatRequest> getSubCategory(@Query("SECKEY") String str, @Query("category_id") String str2);

}
