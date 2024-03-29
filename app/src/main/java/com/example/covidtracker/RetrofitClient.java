package com.example.covidtracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static MyApi myApi;
    private RetrofitClient() {

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.covid19india.org/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApi = retrofit.create(MyApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public MyApi getMyApi() {
        return myApi;
    }
}