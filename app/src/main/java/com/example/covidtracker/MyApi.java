package com.example.covidtracker;

import com.example.covidtracker.models.RootData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {

    @GET("data.json")
    Call<RootData> getTeams();
}
