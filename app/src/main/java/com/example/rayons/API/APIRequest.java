package com.example.rayons.API;

import com.example.rayons.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequest {

    @GET("getKapal.php")
    Call<ResponseModel> getKapal();

    @FormUrlEncoded
    @POST("Register.php")
    Call<ResponseModel> createUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Auth.php")
    Call<ResponseModel> authUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
