package com.acdemo.hashsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.activities.SearchActivity;
import com.acdemo.hashsearch.adapters.HistoryAdapter;
import com.acdemo.hashsearch.adapters.OnRecycleItemClickListener;
import com.acdemo.hashsearch.models.SearchResponse;
import com.acdemo.hashsearch.models.Tweets;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 1/15/17.
 */

public class HistoryFragment extends Fragment implements OnRecycleItemClickListener {

    public static final String TAG = HistoryFragment.class.getSimpleName();

    private RecyclerView historyList;
    private HistoryAdapter adapter;
    private List<SearchResponse> responseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle){
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle icicle){
        historyList = (RecyclerView) view.findViewById(R.id.history_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historyList.setLayoutManager(manager);
        ((SearchActivity) getActivity()).getToggle().setDrawerIndicatorEnabled(true);

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getHistory();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(responseList!=null)
            setAdapter();
    }

    private void setAdapter(){
        if(adapter==null || historyList.getAdapter()==null) {
            adapter = new HistoryAdapter(getActivity(), responseList);
            adapter.setRecycleItemClickListener(this);
            historyList.setAdapter(adapter);
        }else{
            adapter.setResponseList(responseList);
            adapter.notifyDataSetChanged();
        }
    }

    private void getHistory(){
        responseList = new Select().from(SearchResponse.class).execute();
        setAdapter();
    }

    @Override
    public void onRecycleItemClicked(int position) {
        List<Tweets> tweetsList = responseList.get(position).getSavedTweets();
        SearchActivity activity = (SearchActivity) getActivity();
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putSerializable("tweets", (Serializable) tweetsList);
        searchFragment.setArguments(args);
        activity.setChildFragment(searchFragment);
    }
}
