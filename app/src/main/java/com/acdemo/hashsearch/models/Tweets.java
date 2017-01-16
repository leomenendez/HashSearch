package com.acdemo.hashsearch.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 1/13/17.
 */

@Table(name="Tweets")
public class Tweets extends Model implements Serializable {

    public Tweets(){
        super();
    }

    @Column(name="IsFavorite")
    @SerializedName("favorited")
    private boolean favorite;

    @Column(name="IsTruncated")
    @SerializedName("truncated")
    private boolean truncated;

    @Column(name="TweetId")
    @SerializedName("id")
    private String tweetID;

    @Column(name="Text")
    @SerializedName("text")
    private String text;

    @Column(name="Retweets")
    @SerializedName("retweet_count")
    private long retweets;

    @Column(name="Retweeted")
    @SerializedName("retweeted")
    private boolean isRetweeted;

    @Column(name = "TimeStamp")
    @SerializedName("created_at")
    private String timeStamp;

    @Column(name = "TweetEntities")
    @SerializedName("entities")
    private TweetEntities entities;

    @Column(name="User")
    @SerializedName("user")
    private User user;

    @Column(name = "Search")
    private SearchResponse searchResponse;

    public void saveAll(){
        user.save();
        entities.saveAll();
        save();
    }


    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getRetweets() {
        return retweets;
    }

    public void setRetweets(long retweets) {
        this.retweets = retweets;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public TweetEntities getEntities() {
        return entities;
    }

    public void setEntities(TweetEntities entities) {
        this.entities = entities;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public SearchResponse getSearchResponse() {
        return searchResponse;
    }

    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    @Table(name = "TweetEntities")
    public static class TweetEntities extends Model implements Serializable {

        public TweetEntities(){
            super();
        }

        @Column(name="Hashtags")
        @SerializedName("hashtags")
        private List<Hashtags> hashtags = new ArrayList<>();

        @Column(name="Media")
        @SerializedName("media")
        private List<Media> media = new ArrayList<>();

        public List<Hashtags> getSavedHashtags(){
            return getMany(Hashtags.class, "TweetEntity");
        }

        public List<Media> getSavedMedia(){
            return getMany(Media.class, "TweetEntity");
        }


        public void saveAll(){
            save();

            for (Hashtags hashtag:hashtags) {
                hashtag.setTweetEntity(this);
                hashtag.save();
            }
            for(Media mediaItem:media) {
                mediaItem.setTweetEntity(this);
                mediaItem.saveAll();
            }

        }


        public List<Hashtags> getHashtags() {
            if(hashtags==null || hashtags.isEmpty())
                //try getting them from saved data
                hashtags = getSavedHashtags();
            return hashtags;
        }

        public void setHashtags(List<Hashtags> hashtags) {
            this.hashtags = hashtags;
        }

        public List<Media> getMedia() {
            if(media==null || media.isEmpty())
                media = getSavedMedia();
            return media;
        }

        public void setMedia(List<Media> media) {
            this.media = media;
        }

        @Table(name = "Hashtags")
        public static class Hashtags extends Model implements Serializable {
            public Hashtags(){
                super();
            }

            @Column(name = "TweetEntity")
            private TweetEntities tweetEntity;

            @Column(name = "Text")
            @SerializedName("text")
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public TweetEntities getTweetEntity() {
                return tweetEntity;
            }

            public void setTweetEntity(TweetEntities tweetEntity) {
                this.tweetEntity = tweetEntity;
            }
        }

    }
}
