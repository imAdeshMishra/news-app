package com.example.android.newsly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class News {

     @SerializedName("articles")
    private List<Articles> articles;

    public List<Articles> getArticles() {
        return articles;
    }
}
