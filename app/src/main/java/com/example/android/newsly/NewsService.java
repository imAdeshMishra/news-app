package com.example.android.newsly;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//https://newsapi.org/v2/top-headlines?country=in&apiKey=162e77b5472149899d44cb68b5207638
public interface NewsService {


    String API_KEY = "162e77b5472149899d44cb68b5207638";

    @GET("v2/top-headlines?apiKey="+API_KEY)
    Call <News> getNews(@Query("country") String country);

//    @GET("/v2/top-headlines?apikey=$API_KEY")
//    fun getHeadLines(@Query("country") country : String, @Query("page") page : Int) : Call<News>
}
