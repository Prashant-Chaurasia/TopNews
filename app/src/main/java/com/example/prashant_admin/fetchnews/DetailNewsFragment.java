package com.example.prashant_admin.fetchnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prashant_admin.fetchnews.model.News;
import com.koushikdutta.ion.Ion;


public class DetailNewsFragment extends Fragment {
    private ImageView detailImage;
    private TextView detailTitle;
    private TextView detailNewsSource;
    private TextView detailDescription;
    private TextView detailNewsPublishedAt;
    private TextView detailNewsUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_news, container, false);
        News news = (News) getArguments().getParcelable("news");

        detailImage = (ImageView) view.findViewById(R.id.DetailImage);
        detailTitle = (TextView) view.findViewById(R.id.DetailTitle);
        detailNewsUrl = (TextView) view.findViewById(R.id.DetailNewsUrl);
        detailNewsSource = (TextView) view.findViewById(R.id.DetailNewsSource);
        detailNewsPublishedAt = (TextView) view.findViewById(R.id.DetailNewsPublishedAt);
        detailDescription = (TextView) view.findViewById(R.id.DetailDescription);

        detailNewsSource.setText(news.getSource().getName());
        detailDescription.setText(news.getDescription());
        detailNewsPublishedAt.setText(news.getPublishedAt());
        detailTitle.setText(news.getTitle());
        detailNewsUrl.setText(news.getUrl());

        Ion.with(detailImage)
                .placeholder(R.drawable.placeholder)
                .load(news.getUrlToImage());
        return view;

    }

}
