package com.audibene.covidalert.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoronaApi {

    @GET("/countries/{code}")
    Call<CoronaApiResponse> getByCountry(@Path("code") String code);
}
