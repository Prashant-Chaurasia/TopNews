package com.example.prashant_admin.fetchnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prashant_admin.fetchnews.R;
import com.example.prashant_admin.fetchnews.model.News;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<News> news;
    private int rowLayout;
    private Context context;

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout newsLayout;
        TextView headline;
        ImageView newsImage;
        TextView newsSource;
        TextView newsPublishedAt;

        public NewsViewHolder(View view) {
            super(view);
            this.newsLayout = (LinearLayout)view.findViewById(R.id.newslayout);
            this.headline = (TextView) view.findViewById(R.id.headline);
            this.newsImage = (ImageView)view.findViewById(R.id.newsImage);
            this.newsSource = (TextView) view.findViewById(R.id.newsSource);
            this.newsPublishedAt = (TextView) view.findViewById(R.id.newsPublishedAt);
        }
    }

    public NewsAdapter(List<News> news, int rowLayout, Context context) {
        this.news = news;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder,final int position) {
        holder.headline.setText(news.get(position).getTitle());
        holder.newsSource.setText(news.get(position).getSource().getName());
        holder.newsPublishedAt.setText(news.get(position).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
