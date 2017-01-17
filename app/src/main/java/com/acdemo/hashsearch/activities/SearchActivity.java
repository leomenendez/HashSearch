package com.acdemo.hashsearch.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.fragments.HistoryFragment;
import com.acdemo.hashsearch.fragments.SearchFragment;
import com.acdemo.hashsearch.models.SearchResponse;
import com.acdemo.hashsearch.network.TwitterWebServiceFactory;
import com.acdemo.hashsearch.network.WebServiceConnection;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = SearchActivity.class.getSimpleName();

    public static final String HOME_FRAGMENT_TAG = "home_fragment";

    private List<View> navigationViews = new LinkedList<>();
    private DrawerLayout drawer;
    private Fragment homeFragment;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toggle.syncState();

        initNavigation();

        homeFragment = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT_TAG);
        if(homeFragment == null)
            setHomeFragment(new SearchFragment());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initNavigation(){
        View search = findViewById(R.id.btn_search);
        View history = findViewById(R.id.btn_history);

        search.setOnClickListener(this);
        history.setOnClickListener(this);

        navigationViews.add(search);
        navigationViews.add(history);
    }

    public void setHomeFragment(Fragment fragment){
        homeFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment, HOME_FRAGMENT_TAG);
        transaction.commit();
    }

    public void setChildFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void testSearch(){
        WebServiceConnection twitterConnection = TwitterWebServiceFactory.getInstance();
        Call<SearchResponse> call = twitterConnection.getTweetSearchResults("#obama", 100);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response!=null) {
                    SearchResponse searchResponse = response.body();
                    Log.d(TAG, String.valueOf(searchResponse.getSearchMetaData().getCount()));
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());

            }

        });


    }

    @Override
    public void onClick(View v) {
        v.setSelected(true);
        switch (v.getId()){
            case R.id.btn_search:
                if(!(homeFragment instanceof SearchFragment))
                    setHomeFragment(new SearchFragment());
                break;
            case R.id.btn_history:
                if(!(homeFragment instanceof HistoryFragment))
                    setHomeFragment(new HistoryFragment());
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        for(View navView:navigationViews){
            if(v.getId()!=navView.getId())
                navView.setSelected(false);
        }
    }

    public ActionBarDrawerToggle getToggle() {
        return toggle;
    }


}
