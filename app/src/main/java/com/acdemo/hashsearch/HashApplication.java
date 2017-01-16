package com.acdemo.hashsearch;

import android.app.Application;

import com.acdemo.hashsearch.network.TwitterWebServiceFactory;
import com.activeandroid.ActiveAndroid;

/**
 * Created by leo on 1/13/17.
 */

public class HashApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "Z9Cm7a0YvDfyKVCd8GmNHnzRT";
    public static final String TWITTER_SECRET = "UGkAzlBnl9BKIgJHZbxfl6XBye5DU7iL6mUGBWwqOonmbfNmaK";

    @Override
    public void onCreate(){
        super.onCreate();
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));

        TwitterWebServiceFactory.getInstance();
        ActiveAndroid.initialize(this);

    }


}
