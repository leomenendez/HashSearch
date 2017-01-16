package com.acdemo.hashsearch.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leo on 1/13/17.
 */

@Table(name = "Users")
public class User extends Model implements Serializable {

    @Column(name="Name")
    @SerializedName("name")
    private String name;

    @Column(name="ProfileImage")
    @SerializedName("profile_image_url")
    private String imageURL;

    @Column(name="Location")
    @SerializedName("location")
    private String location;

    @Column(name="UserId")
    @SerializedName("userID")
    private long userID;

    @Column(name="FollowerCount")
    @SerializedName("followers_count")
    private long followerCount;

    @Column(name="Verified")
    @SerializedName("verified")
    private boolean Verified;

    @Column(name="TweetCount")
    @SerializedName("statuses_count")
    private long tweetCount;

    @Column(name="FriendCount")
    @SerializedName("friends_count")
    private long friendCount;

    @Column(name="ScreenName")
    @SerializedName("screen_name")
    private String screenName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public boolean isVerified() {
        return Verified;
    }

    public void setVerified(boolean verified) {
        Verified = verified;
    }

    public long getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(long tweetCount) {
        this.tweetCount = tweetCount;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}


