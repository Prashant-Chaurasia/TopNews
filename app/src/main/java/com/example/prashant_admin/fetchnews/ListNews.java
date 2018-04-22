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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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
    private Map<String, String> data = new HashMap<>();
    private RecyclerViewOnItemClickListener listener;
    private List<News> news = new ArrayList<News>();
    private RotateLoading rotateLoading;
    private String country = "in";
    private String category;
    private Button filter;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news,
                container, false);
        filter = (Button)view.findViewById(R.id.filter);
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(news.size() == 0){
            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsFilter(v);
                }
            });
            callToGetData();
        }
        else{
            rotateLoading.stop();
            setRecyclerViewOnItemClickListener();
            recyclerView.setAdapter(new NewsAdapter(news,R.layout.listitem,getActivity(),listener));
        }

        return view;
    }

    public void callToGetData() {
        rotateLoading.start();
        data.put("country", country);
        data.put("apiKey", API_KEY);
        if(category!=null){
            data.put("category",category);
        }
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
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setHomeActive(false);
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
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

    public void newsFilter(View view) {

        final String filterOption[] = {"Country","Category"};
        final Map<String,String> countryList = new HashMap<String,String>();
        final String countries[] = {"Russia","India","USA","Australia","Japan"};
        countryList.put(countries[0],"ru");
        countryList.put(countries[1],"in");
        countryList.put(countries[2],"us");
        countryList.put(countries[3],"au");
        countryList.put(countries[4],"jp");
        final String categoryList[] = {"entertainment","general","health","science","sports","technology"};
        new MaterialDialog.Builder(Objects.requireNonNull(this.getActivity()))
                .title(R.string.filter)
                .items(filterOption)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
                        if(which == 0) {
                            new MaterialDialog.Builder(Objects.requireNonNull(getContext()))
                                    .title(filterOption[0])
                                    .items(countries)
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog1, View itemView1, int i, CharSequence text1) {
                                            country = countryList.get(countries[i]);
                                            callToGetData();
                                            return true;
                                        }

                                    })
                            .positiveText(R.string.choose)
                            .show();
                        }
                        else {
                            new MaterialDialog.Builder(Objects.requireNonNull(getContext()))
                                    .title(filterOption[1])
                                    .items(categoryList)
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog1, View itemView1, int i, CharSequence text1) {
                                            category = categoryList[i];
                                            callToGetData();
                                            return true;
                                        }
                                    })
                                    .positiveText(R.string.choose)
                                    .show();
                        }
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

}
