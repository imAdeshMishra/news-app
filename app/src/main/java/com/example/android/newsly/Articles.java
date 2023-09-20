package com.example.android.newsly;

public class Articles {

    private String author;
    private String title;
    private String description;
    private String publishedAt;
    private String urlToImage;
    private String url;

    public Articles(String author, String title, String description, String publishedAt, String urlToImage, String url) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {return publishedAt;}

    public String getUrlToImage() {return urlToImage;}

    public String getUrl() {return url;}
}
