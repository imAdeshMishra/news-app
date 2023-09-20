package com.example.android.newsly;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {

    List<Articles> news;

    public NewsAdapter(Context context, List<Articles> news) {
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_data,parent,false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        String dateString = news.get(position).getPublishedAt();
        dateString.substring(0,dateString.indexOf("T"));
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        try {
            Date date = inputDateFormat.parse(dateString);
            String formattedDate = outputDateFormat.format(date);
            dateString = formattedDate;
        } catch (Exception e) {
            dateString = dateString + "";
        }

        holder.publishedAt_textView.setText(dateString);
        String author = news.get(position).getAuthor();
        if (TextUtils.isEmpty(author)){
            holder.author_textView.setVisibility(View.GONE);
        }else{
            holder.author_textView.setText(author);
        }

        holder.description_textView.setText(news.get(position).getDescription());
        holder.title_textView.setText(news.get(position).getTitle());
        Picasso.get().load(news.get(position).getUrlToImage()).into(holder.news_image);

        Uri newsUrl = Uri.parse(news.get(position).getUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUrl);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView author_textView;
        TextView title_textView;
        TextView description_textView;
        TextView publishedAt_textView;
        ImageView news_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author_textView = itemView.findViewById(R.id.author);
            title_textView = itemView.findViewById(R.id.title);
            description_textView = itemView.findViewById(R.id.description);
            publishedAt_textView = itemView.findViewById(R.id.published_at);
            news_image = itemView.findViewById(R.id.news_image);
        }
    }
}