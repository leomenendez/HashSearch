package com.acdemo.hashsearch.models;

import java.util.Comparator;

/**
 * Created by leo on 1/15/17.
 */

public class TweetCompare implements Comparator<Tweets> {
    @Override
    public int compare(Tweets t1, Tweets t2) {
        return (int) Math.signum(t2.getUser().getFollowerCount()-t1.getUser().getFollowerCount());
    }
}
