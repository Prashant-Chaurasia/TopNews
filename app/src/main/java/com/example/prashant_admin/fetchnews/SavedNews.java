package com.example.prashant_admin.fetchnews;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant_admin.fetchnews.adapter.NewsAdapter;
import com.example.prashant_admin.fetchnews.database.NewsContract;
import com.example.prashant_admin.fetchnews.database.NewsDBHelper;
import com.example.prashant_admin.fetchnews.model.News;
import com.example.prashant_admin.fetchnews.model.Source;
import com.example.prashant_admin.fetchnews.util.RecyclerViewOnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.prashant_admin.fetchnews.database.NewsContract.*;

public class SavedNews extends Fragment{
    NewsDBHelper newsDBHelper;
    SQLiteDatabase dbNews;
    RecyclerViewOnItemClickListener listener;
    List<News> news = new ArrayList<News>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news,
                container, false);
        newsDBHelper = new NewsDBHelper(getContext());
        dbNews = newsDBHelper.getReadableDatabase();
        String sortOrder = "saveTime";
        Cursor cursor = dbNews.query(
                NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                 sortOrder
        );
        if(news.size() == 0) {
            while(cursor.moveToNext()) {
                News n = new News();
                Source source = new Source();
                source.setId(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_ID)));
                source.setName(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NAME)));
                n.setSource(source);
                n.setAuthor(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_AUTHOR)));
                n.setTitle(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_TITLE)));
                n.setDescription(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_DESCRIPTION)));
                n.setUrl(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_URL)));
                n.setUrlToImage(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_URL_TO_IMAGE)));
                n.setPublishedAt(cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_PUBLISHED_AT)));
                news.add(n);
            }
        }
        cursor.close();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setRecyclerViewOnItemClickListener();
        recyclerView.setAdapter(new NewsAdapter(news,R.layout.listitem,getActivity(),listener));
        return view;
    }

    public void setRecyclerViewOnItemClickListener() {
        listener = new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Fragment fragment = new SavedNewsDetailFragment();
                Bundle bundle = new Bundle();
                News n = news.get(position);
                bundle.putParcelable("news", n);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                        .addToBackStack("savednews")
                        .commit();
            }
        };
    }
}
