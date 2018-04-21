package com.example.prashant_admin.fetchnews;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.prashant_admin.fetchnews.database.NewsContract.*;


public class SavedNewsDetailFragment extends Fragment {
    private ImageView detailImage;
    private TextView detailTitle;
    private TextView detailNewsSource;
    private TextView detailDescription;
    private TextView detailNewsPublishedAt;
    private TextView detailNewsUrl;
    private TextView detailNewsAuthor;
    private NewsDBHelper newsDBHelper;
    private SQLiteDatabase sqLiteDatabase;
    private List<String> contentValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_news, container, false);
        News news = (News) getArguments().getParcelable("news");

        newsDBHelper = new NewsDBHelper(getContext());
        sqLiteDatabase = newsDBHelper.getWritableDatabase();
        contentValues = new ArrayList<String>();

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

        //Arguments to pass in sql query as they will be non - null;
        contentValues.add(news.getUrl());
        contentValues.add(news.getUrlToImage());
        contentValues.add(news.getPublishedAt());

        Ion.with(detailImage)
                .placeholder(R.drawable.placeholder)
                .load(news.getUrlToImage());
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.saved_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                sqLiteDatabase.delete(NewsEntry.TABLE_NAME,
                         NewsEntry.COLUMN_URL + " = ? AND "+ NewsEntry.COLUMN_URL_TO_IMAGE + " = ? AND " + NewsEntry.COLUMN_PUBLISHED_AT + " = ?",
                        contentValues.toArray(new String[contentValues.size()]));
                Toast.makeText(getContext(),"Successfully Deleted the Post",Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                Fragment fragment = new SavedNews();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_save_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
