package com.example.prashant_admin.fetchnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;

import com.example.prashant_admin.fetchnews.database.NewsContract;
import com.example.prashant_admin.fetchnews.database.NewsDBHelper;
import com.example.prashant_admin.fetchnews.model.News;
import com.koushikdutta.ion.Ion;

import java.util.Objects;

import static com.example.prashant_admin.fetchnews.database.NewsContract.*;


public class DetailNewsFragment extends Fragment {
    private ImageView detailImage;
    private TextView detailTitle;
    private TextView detailNewsSource;
    private TextView detailDescription;
    private TextView detailNewsPublishedAt;
    private TextView detailNewsUrl;
    private TextView detailNewsAuthor;
    private NewsDBHelper newsDBHelper;
    private SQLiteDatabase sqLiteDatabaseWrite;
    private SQLiteDatabase sqLiteDatabaseRead;
    private ContentValues contentValues;
    private Boolean newsExist = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_news, container, false);
        News news = (News) getArguments().getParcelable("news");

        (Objects.requireNonNull(getActivity())).setTitle(R.string.post);

        newsDBHelper = new NewsDBHelper(getContext());
        sqLiteDatabaseWrite = newsDBHelper.getWritableDatabase();
        sqLiteDatabaseRead = newsDBHelper.getReadableDatabase();
        contentValues = new ContentValues();

        detailImage = (ImageView) view.findViewById(R.id.DetailImage);
        detailTitle = (TextView) view.findViewById(R.id.DetailTitle);
        detailNewsUrl = (TextView) view.findViewById(R.id.DetailNewsUrl);
        detailNewsSource = (TextView) view.findViewById(R.id.DetailNewsSource);
        detailNewsPublishedAt = (TextView) view.findViewById(R.id.DetailNewsPublishedAt);
        detailDescription = (TextView) view.findViewById(R.id.DetailDescription);
        detailNewsAuthor = (TextView) view.findViewById(R.id.DetailAuthor);

        detailNewsSource.setText(news.getSource().getName());
        detailDescription.setText(news.getDescription());
        detailNewsPublishedAt.setText(news.getPublishedAt());
        detailTitle.setText(news.getTitle());
        detailNewsUrl.setText(news.getUrl());
        detailNewsAuthor.setText(news.getAuthor());

        newsExist = entryExist(news);
        if(!newsExist) {
            contentValues.put(NewsEntry.COLUMN_ID, news.getSource().getId());
            contentValues.put(NewsEntry.COLUMN_NAME, news.getSource().getName());
            contentValues.put(NewsEntry.COLUMN_AUTHOR, news.getAuthor());
            contentValues.put(NewsEntry.COLUMN_DESCRIPTION, news.getDescription());
            contentValues.put(NewsEntry.COLUMN_TITLE, news.getTitle());
            contentValues.put(NewsEntry.COLUMN_URL, news.getUrl());
            contentValues.put(NewsEntry.COLUMN_URL_TO_IMAGE, news.getUrlToImage());
            contentValues.put(NewsEntry.COLUMN_PUBLISHED_AT, news.getPublishedAt());
        }

        Ion.with(detailImage)
                .placeholder(R.drawable.placeholder)
                .load(news.getUrlToImage());
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu,menu);
        if(newsExist)
            menu.getItem(0).setIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_star_white_24dp));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                if(!newsExist){
                    sqLiteDatabaseWrite.insert(NewsEntry.TABLE_NAME,null,contentValues);
                    item.setIcon(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),R.drawable.ic_star_white_24dp));
                }
                else{
                    Toast.makeText(getContext(),"Post Already Saved",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean entryExist(News news)
    {
        Cursor cursor = null;
        String checkQuery = "SELECT * " + " FROM " + NewsEntry.TABLE_NAME + " WHERE "
                + NewsEntry.COLUMN_URL + " = '" + news.getUrl() + "' AND "
                + NewsEntry.COLUMN_PUBLISHED_AT + " = '" + news.getPublishedAt() +"'";
        cursor= sqLiteDatabaseRead.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
