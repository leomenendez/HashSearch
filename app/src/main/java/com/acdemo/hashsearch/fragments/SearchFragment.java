package com.acdemo.hashsearch.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.activities.SearchActivity;
import com.acdemo.hashsearch.adapters.OnRecycleItemClickListener;
import com.acdemo.hashsearch.adapters.TweetSearchAdapter;
import com.acdemo.hashsearch.models.SearchResponse;
import com.acdemo.hashsearch.models.TweetCompare;
import com.acdemo.hashsearch.models.Tweets;
import com.acdemo.hashsearch.network.TwitterWebServiceFactory;
import com.acdemo.hashsearch.network.WebServiceConnection;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leo on 1/14/17.
 */

public class SearchFragment extends Fragment implements OnRecycleItemClickListener {

    public static final String TAG = SearchFragment.class.getSimpleName();

    public static final int REFRESH_INTERVAL = 30 *1000;
    public static final int POPUP_TIMEOUT = 5*1000;

    private RecyclerView searchList;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;
    private TweetSearchAdapter adapter;
    private List<Tweets> tweetsList;
    private String searchQuery;
    private Handler handler = new Handler();

    private boolean stopRefresh = false;
    private boolean showDrawer = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle){
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle icicle){
        searchList = (RecyclerView) view.findViewById(R.id.search_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchList.setLayoutManager(manager);
        searchView = (SearchView) view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                searchView.clearFocus();
                swipeRefresh.setRefreshing(true);
                refreshSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(stopRefresh) {
                    swipeRefresh.setRefreshing(false);
                    return;
                }
                swipeRefresh.setRefreshing(true);
                refreshSearch();
            }
        });

        stopRefresh = false;
        if(icicle == null) {
            icicle = getArguments();

            if (icicle != null) {
                tweetsList = (List<Tweets>) icicle.getSerializable("tweets");
                if (tweetsList != null) {
                    Collections.sort(tweetsList, new TweetCompare());
                    stopRefresh = true;
                    showDrawer = false;
                    searchView.setVisibility(View.GONE);
                }
            }
        }

        ((SearchActivity) getActivity()).getToggle().setDrawerIndicatorEnabled(showDrawer);
    }

    @Override
    public void onSaveInstanceState(Bundle icicle){
        if(icicle==null)
            icicle = new Bundle();
        icicle.putSerializable("tweets", (Serializable) tweetsList);
        icicle.putBoolean("showDrawer", showDrawer);
        super.onSaveInstanceState(icicle);
    }

    @Override
    public void onViewStateRestored(Bundle icicle){
        super.onViewStateRestored(icicle);

        if(icicle!=null){
            tweetsList = (List<Tweets>) icicle.getSerializable("tweets");
            showDrawer = icicle.getBoolean("showDrawer");

            ((SearchActivity) getActivity()).getToggle().setDrawerIndicatorEnabled(showDrawer);

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(tweetsList!=null)
            setAdapter();
    }

    @Override
    public void onPause(){
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRecycleItemClicked(int position) {
        Tweets tweet = tweetsList.get(position);
        Fragment tweetFragment = new TweetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tweet", tweet);
        tweetFragment.setArguments(bundle);
        ((SearchActivity) getActivity()).setChildFragment(tweetFragment);
    }

    private void setAdapter(){
        swipeRefresh.setRefreshing(false);
        if(adapter==null || searchList.getAdapter()==null) {
            adapter = new TweetSearchAdapter(getActivity(), tweetsList);
            searchList.setAdapter(adapter);
            adapter.setRecycleItemClickListener(this);
        }else{
            adapter.setTweetsList(tweetsList);
            adapter.notifyDataSetChanged();
        }
    }

    private void refreshSearch(){
        handler.removeCallbacksAndMessages(null);
        findTweets(getFormattedTags(searchQuery));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(getView(), getString(R.string.refresh_search), POPUP_TIMEOUT).show();
                refreshSearch();
            }
        }, REFRESH_INTERVAL);
    }

    private void findTweets(String string){

        WebServiceConnection twitterConnection = TwitterWebServiceFactory.getInstance();
        Call<SearchResponse> call = twitterConnection.getTweetSearchResults(URLEncoder.encode(string), 100);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response!=null) {
                    SearchResponse searchResponse = response.body();
                    if(searchResponse==null)
                        return;

                    Log.d(TAG, String.valueOf(searchResponse.getSearchMetaData().getCount()));

                    tweetsList = searchResponse.getTweetsList();
                    Collections.sort(tweetsList, new TweetCompare());
                    setAdapter();

                    persistResponse(searchResponse);

                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());

            }

        });

    }

    private void persistResponse(final SearchResponse searchResponse){
        String saveQuery = getFormattedTags(searchQuery).replace("+", ", ");

        ActiveAndroid.beginTransaction();
        try {
            SearchResponse persistResponse = new Select().from(SearchResponse.class).where("searchUniqueID = ?", saveQuery).executeSingle();
            if (persistResponse == null)
                persistResponse = searchResponse;
            else {
                new Delete().from(Tweets.class).where("Search = ?", persistResponse.getId()).execute();
                persistResponse.setTweetsList(searchResponse.getTweetsList());
                persistResponse.setSearchMetaData(searchResponse.getSearchMetaData());
            }
            persistResponse.setSearchID(saveQuery);
            persistResponse.setDate(Calendar.getInstance().getTime().toString());
            persistResponse.saveAll();

            ActiveAndroid.setTransactionSuccessful();
        }finally {
            ActiveAndroid.endTransaction();
        }
    }

    private String getFormattedTags(String searchString){
        String[] hashtags = searchString.split("(, )|,| ");
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<hashtags.length; i++){
            if(!hashtags[i].startsWith("#"))
                builder.append("#");
            builder.append(hashtags[i]);
            if(i<hashtags.length-1)
                builder.append("+");
        }
        return builder.toString();


    }

}
