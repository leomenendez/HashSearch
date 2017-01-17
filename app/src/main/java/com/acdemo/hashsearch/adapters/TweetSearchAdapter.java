package com.acdemo.hashsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.models.Tweets;
import com.acdemo.hashsearch.views.TweetViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by leo on 1/14/17.
 */

public class TweetSearchAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    public static final int TYPE_TWEET = 0;
    public static final int TYPE_HEADER = 1;

    private List<Tweets> tweetsList;
    private Context context;
    private int offset;

    private OnRecycleItemClickListener recycleItemClickListener;

    public TweetSearchAdapter(Context context, List<Tweets> tweetsList){
        this.context = context;
        this.tweetsList = tweetsList;
    }

    public void setTweetsList(List<Tweets> tweetsList) {
        this.tweetsList = tweetsList;
    }

    @Override
    public int getItemViewType(int position){
        if(position==0||position==11){
            return TYPE_HEADER;
        }
        return TYPE_TWEET;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_TWEET:
                view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
                view.setOnClickListener(clickListener);
                break;
            case TYPE_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
                view.setOnClickListener(null);
                offset++;
                break;
        }
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            if(position==0)
                holder.headerTitle.setText(context.getString(R.string.header_top_results));
            else if(position==11)
                holder.headerTitle.setText(context.getString(R.string.header_more_results));

            return;
        }

        int listPosition = position-offset;
        Tweets tweet = tweetsList.get(listPosition);
        if(tweet.getEntities().getMedia()==null || tweet.getEntities().getMedia().isEmpty())
            holder.mediaContent.setVisibility(View.GONE);
        else {
            holder.mediaContent.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                    .into(holder.mediaView);
        }

        if(tweet.getUser().isVerified())
            holder.verifiedUser.setVisibility(View.VISIBLE);
        else
            holder.verifiedUser.setVisibility(View.GONE);

        Glide.with(context)
                .load(tweet.getUser().getImageURL())
                .asBitmap()
                .into(holder.userImage);
        holder.userName.setText(tweet.getUser().getName());
        holder.tweetText.setText(tweet.getText());
        holder.timeStamp.setText(tweet.getTimeStamp());
        holder.userHandle.setText("@"+tweet.getUser().getScreenName());
        holder.setTag(listPosition);
    }

    @Override
    public int getItemCount() {
        return tweetsList.size()+2;
    }

    public void setRecycleItemClickListener(OnRecycleItemClickListener recycleItemClickListener) {
        this.recycleItemClickListener = recycleItemClickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(recycleItemClickListener!=null){
                recycleItemClickListener.onRecycleItemClicked((int) v.getTag());
            }
        }
    };

}
