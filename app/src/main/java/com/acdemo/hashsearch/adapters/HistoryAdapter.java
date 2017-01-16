package com.acdemo.hashsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.models.SearchResponse;
import com.acdemo.hashsearch.views.HistoryViewHolder;

import java.util.List;

/**
 * Created by leo on 1/14/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private List<SearchResponse> responseList;
    private Context context;

    private OnRecycleItemClickListener recycleItemClickListener;
    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView){
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public HistoryAdapter(Context context, List<SearchResponse> responseList){
        this.context = context;
        this.responseList = responseList;
    }

    public void setResponseList(List<SearchResponse> responseList) {
        this.responseList = responseList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        view.setOnClickListener(clickListener);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        SearchResponse searchResponse = responseList.get(position);
        holder.searchQuery.setText(searchResponse.getSearchID());
        holder.timeStamp.setText(searchResponse.getDate());
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public void setRecycleItemClickListener(OnRecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(recycleItemClickListener!=null){
                recycleItemClickListener.onRecycleItemClicked(recyclerView.indexOfChild(v));
            }
        }
    };

}
