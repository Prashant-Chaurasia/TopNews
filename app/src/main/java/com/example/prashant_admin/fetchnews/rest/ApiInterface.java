package com.example.prashant_admin.fetchnews.rest;

import com.example.prashant_admin.fetchnews.model.NewsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("/v2/")
    Call<NewsResponse> getSources();

    @GET("v2/top-headlines")
    Call<NewsResponse> getTopHeadLines(@QueryMap Map<String,String> options);
}
