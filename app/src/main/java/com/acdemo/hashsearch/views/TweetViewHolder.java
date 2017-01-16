package com.acdemo.hashsearch.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acdemo.hashsearch.R;

/**
 * Created by leo on 1/14/17.
 */

public class TweetViewHolder extends RecyclerView.ViewHolder {
    public View mediaContent;
    public ImageView userImage, mediaView, verifiedUser;
    public TextView userName, tweetText, timeStamp, headerTitle;

    private View itemView;

    public TweetViewHolder(View itemView) {
        super(itemView);
        mediaContent = itemView.findViewById(R.id.media_content);
        mediaView = (ImageView) itemView.findViewById(R.id.media_image);
        userImage = (ImageView) itemView.findViewById(R.id.user_image);
        userName = (TextView) itemView.findViewById(R.id.user_name);
        tweetText = (TextView) itemView.findViewById(R.id.tweet_text);
        timeStamp = (TextView) itemView.findViewById(R.id.time_stamp);
        headerTitle = (TextView) itemView.findViewById(R.id.header_title);
        verifiedUser = (ImageView) itemView.findViewById(R.id.verified_user);

        this.itemView = itemView;
    }

    public void setTag(int tag){
        itemView.setTag(tag);
    }
}
