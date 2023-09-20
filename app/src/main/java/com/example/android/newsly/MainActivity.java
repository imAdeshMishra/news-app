package com.example.android.newsly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    String BASE_URL = "https://newsapi.org/";
    String API_KEY = "162e77b5472149899d44cb68b5207638";
    NewsAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Articles> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        getNews();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                articles.clear();
                // Refresh your data here
                getNews();
                // Once the data has been refreshed, call setRefreshing(false) to hide the progress indicator
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void getNews(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);


        String country = sharedPrefs.getString(
                getString(R.string.settings_country_key),
                getString(R.string.settings_country_default)
        );



        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("country", country)
                        .build();

                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL.toString())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();



        NewsService newsService = retrofit.create(NewsService.class);


        Call<News> call = newsService.getNews(country);

        call.enqueue(new Callback<News>() {

            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error "+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                News newsList = response.body();
                articles = newsList.getArticles();

                for (int i=0;i<articles.size();i++){
                    Articles article = articles.get(i);


                }
                adapter = new NewsAdapter(getApplicationContext(),articles);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
