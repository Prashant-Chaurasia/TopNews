package com.example.prashant_admin.fetchnews;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "listnews";
    private final static String API_KEY = "2afd7b026354408f8b13ae1ff7a5b836";
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private int lastSelectedNavItem = -1;
    private boolean isHomeActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nvView);
        View headerLayout = navigationView.getHeaderView(0);
        setupDrawerContent(navigationView);
        actionBarDrawerToggle = setupDrawerToggle();
        Fragment fragment = new ListNews();
        lastSelectedNavItem = R.id.nav_home_fragment;
        setTitle(R.string.home);
        navigationView.setCheckedItem(R.id.nav_home_fragment);
        isHomeActive = true;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }else if (isHomeActive){
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
            navigationView.getMenu().getItem(0).setChecked(true);
            setTitle(R.string.home);
            lastSelectedNavItem = R.id.nav_home_fragment;
            isHomeActive = true;
        }
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        if(lastSelectedNavItem == menuItem.getItemId())
            return;

        lastSelectedNavItem = menuItem.getItemId();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_saved_fragment:
                fragmentClass = SavedNews.class;
                break;
            case R.id.nav_home_fragment:
                fragmentClass = ListNews.class;
            default:
                fragmentClass = ListNews.class;

        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().popBackStack();
        isHomeActive = R.id.nav_home_fragment == lastSelectedNavItem;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isHomeActive) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }

        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.drawer_open,  R.string.drawer_close);
    }

    @Override

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
