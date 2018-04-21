package com.example.prashant_admin.fetchnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.prashant_admin.fetchnews.util.RecyclerViewOnItemClickListener;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends Fragment {

    private static final String TAG = "listnews";
    private final static String API_KEY = "2afd7b026354408f8b13ae1ff7a5b836";
    Map<String, String> data = new HashMap<>();
    RecyclerViewOnItemClickListener listener;
    List<News> news = new ArrayList<News>();
    private RotateLoading rotateLoading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news,
                container, false);
        data.put("country", "in");
        data.put("apiKey", API_KEY);
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        rotateLoading.start();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(news.size() == 0){
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<NewsResponse> call = api.getTopHeadLines(data);
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                    rotateLoading.stop();
                    news = response.body().getArticles();
                    setRecyclerViewOnItemClickListener();
                    recyclerView.setAdapter(new NewsAdapter(news,R.layout.listitem,getActivity(),listener));
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
        else{
            setRecyclerViewOnItemClickListener();
            recyclerView.setAdapter(new NewsAdapter(news,R.layout.listitem,getActivity(),listener));
        }
        return view;
    }

    public void setRecyclerViewOnItemClickListener(){
        listener = new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Fragment detailNewsFragment = new DetailNewsFragment();
                Bundle bundle = new Bundle();
                News n = news.get(position);
                bundle.putParcelable("news",n);
                detailNewsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container,detailNewsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle(R.string.home);
    }
}
