package com.acdemo.hashsearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acdemo.hashsearch.R;
import com.acdemo.hashsearch.activities.SearchActivity;
import com.acdemo.hashsearch.models.Tweets;
import com.bumptech.glide.Glide;

/**
 * Created by leo on 1/15/17.
 */

public class TweetFragment extends Fragment implements View.OnClickListener {

    public View mediaContent;
    public ImageView userImage, mediaView, verifiedUser;
    public TextView userName, tweetText, timeStamp;
    
    private Tweets tweet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle){
        SearchActivity activity = (SearchActivity) getActivity();
//        ActionBar actionBar = activity.getSupportActionBar();
        activity.getToggle().setDrawerIndicatorEnabled(false);
        activity.getToggle().syncState();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle icicle){
        mediaContent = view.findViewById(R.id.media_content);
        mediaView = (ImageView) view.findViewById(R.id.media_image);
        userImage = (ImageView) view.findViewById(R.id.user_image);
        userName = (TextView) view.findViewById(R.id.user_name);
        tweetText = (TextView) view.findViewById(R.id.tweet_text);
        timeStamp = (TextView) view.findViewById(R.id.time_stamp);
        verifiedUser = (ImageView) view.findViewById(R.id.verified_user);

        if(icicle==null)
            icicle = getArguments();

        if(icicle!=null){
            tweet = (Tweets) icicle.getSerializable("tweet");
        }
        
        if(tweet!=null){
            initTweet();
        }
    }
    
    private  void initTweet(){
        if(tweet.getEntities().getMedia()==null || tweet.getEntities().getMedia().isEmpty())
            mediaContent.setVisibility(View.GONE);
        else {
            mediaContent.setVisibility(View.VISIBLE);
            Glide.with(getActivity())
                    .load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                    .into(mediaView);
        }

        if(tweet.getUser().isVerified())
            verifiedUser.setVisibility(View.VISIBLE);
        else
            verifiedUser.setVisibility(View.GONE);

        Glide.with(getActivity())
                .load(tweet.getUser().getImageURL())
                .asBitmap()
                .into(userImage);
        userName.setText(tweet.getUser().getScreenName());
        tweetText.setText(tweet.getText());
        timeStamp.setText(tweet.getTimeStamp());

    }
    
    @Override
    public void onClick(View v) {
        
    }

    public void setTweet(Tweets tweet) {
        this.tweet = tweet;
    }
}
