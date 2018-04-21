package com.example.prashant_admin.fetchnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prashant_admin.fetchnews.database.NewsContract;
import com.example.prashant_admin.fetchnews.database.NewsDBHelper;
import com.example.prashant_admin.fetchnews.model.News;
import com.koushikdutta.ion.Ion;


public class DetailNewsFragment extends Fragment {
    private ImageView detailImage;
    private TextView detailTitle;
    private TextView detailNewsSource;
    private TextView detailDescription;
    private TextView detailNewsPublishedAt;
    private TextView detailNewsUrl;
    private NewsDBHelper newsDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_news, container, false);
        News news = (News) getArguments().getParcelable("news");

        newsDBHelper = new NewsDBHelper(getContext());
        sqLiteDatabase = newsDBHelper.getWritableDatabase();
        contentValues = new ContentValues();

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

        contentValues.put(NewsContract.NewsEntry.COLUMN_ID,news.getSource().getId());
        contentValues.put(NewsContract.NewsEntry.COLUMN_NAME,news.getSource().getName());
        contentValues.put(NewsContract.NewsEntry.COLUMN_AUTHOR,news.getAuthor());
        contentValues.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION,news.getDescription());
        contentValues.put(NewsContract.NewsEntry.COLUMN_TITLE,news.getTitle());
        contentValues.put(NewsContract.NewsEntry.COLUMN_URL,news.getUrl());
        contentValues.put(NewsContract.NewsEntry.COLUMN_URL_TO_IMAGE,news.getUrlToImage());
        contentValues.put(NewsContract.NewsEntry.COLUMN_PUBLISHED_AT,news.getPublishedAt());

        Ion.with(detailImage)
                .placeholder(R.drawable.placeholder)
                .load(news.getUrlToImage());
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                sqLiteDatabase.insert(NewsContract.NewsEntry.TABLE_NAME,null,contentValues);
                item.setIcon(ContextCompat.getDrawable(getContext(),android.R.drawable.star_big_on));
                break;
            case R.id.menu_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
