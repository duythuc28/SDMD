package com.example.swinburne.accessiblitymap.Manager.RequestAPI;

import com.example.swinburne.accessiblitymap.Model.Building;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RequestDataInterface {
    @Headers("X-App-Token: GxusT0nELv09s1GmHnb1osV1d")
    @GET("q8hp-qgps.json")
    Call<List<Building>> getBuildingInRange( @QueryMap(encoded = false) Map<String, String> options);

    @Headers("X-App-Token: GxusT0nELv09s1GmHnb1osV1d")
    @GET("q8hp-qgps.json")
    Call<List<Building>> getBuildingByName( @QueryMap(encoded = false) Map<String, String> options);
}
