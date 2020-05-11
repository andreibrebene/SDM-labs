package com.app.lab5;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://api.open-notify.org/";
    @GET("astros.json")
    Call<Pojo> getJsonObjectData();

}
