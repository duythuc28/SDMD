package com.example.swinburne.accessiblitymap.Manager.RequestAPI;

import android.util.Log;
import android.widget.Toast;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.PaginationRequest;
import com.example.swinburne.accessiblitymap.Shared.ErrorHandlingClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestAPIManager {
    public static void getBuilding(int radius, double latidude, double longitude, String filterData, final RequestHandler<List<Building>> callback) {
        String range = String.format("%s,%f,%f,%d)", "within_circle(location",latidude, longitude, radius);
        String selectString = "block_id, accessibility_rating, accessibility_type, accessibility_type_description, lower(building_name), location, street_address,suburb,x_coordinate,y_coordinate";
        String whereString = "building_name!='' AND accessibility_type!='' AND census_year=2016 AND " + range;
        if (!filterData.equals("all")) {
            whereString = whereString + " AND " + filterData;
        }
        String groupString = "block_id, accessibility_rating, accessibility_type, accessibility_type_description, lower(building_name), location, street_address,suburb,x_coordinate,y_coordinate";
        Map<String, String> data = new HashMap<>();
        data.put("$where",whereString);
        data.put("$select",selectString);
        data.put("$group",groupString);
        RequestDataInterface service = RetrofitClientInstance.getRetrofitInstance().create(RequestDataInterface.class);
        Call<List<Building>> call = service.getBuildingInRange(data);
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    String errorMessage = ErrorHandlingClass.handlingRequestError(response.code());
                    callback.onFailure(errorMessage);
                }
            }
            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
                Log.e("Error", "network failure :( inform the user and possibly retry");
                callback.onFailure("Network failure");
            }
        });
    }

    public static void getBuildingByName(String name, PaginationRequest page, String filterData,final RequestHandler<List<Building>> callback) {
        String whereString = "building_name!='' AND accessibility_type!='' AND census_year=2016";
        String findName;
        if (name != null) {
            findName = "starts_with(lower(building_name),'" + name +"')";
            whereString = whereString + " AND " + findName;
        }

        if (!filterData.equals("all")) {
            whereString = whereString + " AND " + filterData;
        }
        String selectString = "block_id, accessibility_rating, accessibility_type, accessibility_type_description, lower(building_name), location, street_address,suburb,x_coordinate,y_coordinate";
        String groupString = "block_id, accessibility_rating, accessibility_type, accessibility_type_description, lower(building_name), location, street_address,suburb,x_coordinate,y_coordinate";
        Map<String, String> data = new HashMap<>();
        data.put("$where",whereString);
        data.put("$select",selectString);
        data.put("$group",groupString);
        data.putAll(page.convertToHashMap());
        RequestDataInterface service = RetrofitClientInstance.getRetrofitInstance().create(RequestDataInterface.class);
        Call<List<Building>> call = service.getBuildingByName(data);
        call.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    String errorMessage = ErrorHandlingClass.handlingRequestError(response.code());
                    callback.onFailure(errorMessage);
                }
            }
            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {
                Log.e("Error", "network failure :( inform the user and possibly retry");
                callback.onFailure("Network failure");
            }
        });
    }
}

