package com.example.rayons.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String BaseURL = "http://192.168.1.7/RestAPI/Rayon/";
    public static Retrofit Server;

    public static Retrofit KonekRetrofit(){
        if (Server == null){
            Server = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return Server;
    }
}
