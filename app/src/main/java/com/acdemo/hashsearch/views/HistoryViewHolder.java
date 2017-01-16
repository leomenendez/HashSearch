package com.acdemo.hashsearch.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.acdemo.hashsearch.R;

/**
 * Created by leo on 1/14/17.
 */

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView searchQuery, timeStamp;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        searchQuery = (TextView) itemView.findViewById(R.id.search_query);
        timeStamp = (TextView) itemView.findViewById(R.id.time_stamp);
    }
}
