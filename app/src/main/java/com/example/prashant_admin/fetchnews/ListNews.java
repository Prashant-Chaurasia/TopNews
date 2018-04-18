package com.example.prashant_admin.fetchnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.prashant_admin.fetchnews.adapter.NewsAdapter;
import com.example.prashant_admin.fetchnews.model.News;
import com.example.prashant_admin.fetchnews.model.NewsResponse;
import com.example.prashant_admin.fetchnews.rest.ApiClient;
import com.example.prashant_admin.fetchnews.rest.ApiInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends Fragment {

    private static final String TAG = "listnews";
    private final static String API_KEY = "2afd7b026354408f8b13ae1ff7a5b836";
    Map<String, String> data = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news,
                container, false);
        Toast.makeText(getContext(),"Inside Fragment",Toast.LENGTH_LONG).show();
        data.put("country", "us");
        data.put("apiKey", API_KEY);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<NewsResponse> call = api.getTopHeadLines(data);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                int statusCode = response.code();
                List<News> news = response.body().getArticles();
                Toast.makeText(getContext(),Integer.toString(news.size()),Toast.LENGTH_LONG).show();
                recyclerView.setAdapter(new NewsAdapter(news,R.layout.listitem,getActivity()));
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
        return view;
    }
}
