package com.example.prashant_admin.fetchnews;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "listnews";
    private final static String API_KEY = "2afd7b026354408f8b13ae1ff7a5b836";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new ListNews();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

    }
}
