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

@Table(name="Searches")
public class SearchResponse extends Model implements Serializable {

    public SearchResponse(){
        super();
    }

    @Column(name="Date")
    private String date;

    @Column(name = "searchUniqueID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String searchID;

    @Column(name = "SearchMeta")
    @SerializedName("search_metadata")
    private SearchMeta searchMetaData;

    @Column(name="Tweets")
    @SerializedName("statuses")
    private List<Tweets> tweetsList = new ArrayList<>();

    public void saveAll(){
        searchMetaData.save();
        save();
        for (Tweets tweet: tweetsList) {
            tweet.setSearchResponse(this);
            tweet.saveAll();
        }
    }

    public List<Tweets> getSavedTweets(){
        return getMany(Tweets.class, "Search");
    }


    public List<Tweets> getTweetsList() {
        return tweetsList;
    }

    public void setTweetsList(List<Tweets> tweetsList) {
        this.tweetsList = tweetsList;
    }

    public SearchMeta getSearchMetaData() {
        return searchMetaData;
    }

    public void setSearchMetaData(SearchMeta searchMetaData) {
        this.searchMetaData = searchMetaData;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Table(name = "SearchMeta")
    public static class SearchMeta extends Model implements Serializable{

        public SearchMeta(){
            super();
        }

        @Column(name="MaxID")
        @SerializedName("max_id")
        private long maxID;

        @Column(name="SinceID")
        @SerializedName("since_id")
        private long sinceID;

        @Column(name="RefreshUrl")
        @SerializedName("refresh_url")
        private String refreshURL;

        @Column(name="NexResults")
        @SerializedName("next_results")
        private String nextResults;

        @Column(name="Count")
        @SerializedName("count")
        private int count;

        @Column(name="Query")
        @SerializedName("query")
        private String query;

        public long getMaxID() {
            return maxID;
        }

        public void setMaxID(long maxID) {
            this.maxID = maxID;
        }

        public long getSinceID() {
            return sinceID;
        }

        public void setSinceID(long sinceID) {
            this.sinceID = sinceID;
        }

        public String getRefreshURL() {
            return refreshURL;
        }

        public void setRefreshURL(String refreshURL) {
            this.refreshURL = refreshURL;
        }

        public String getNextResults() {
            return nextResults;
        }

        public void setNextResults(String nextResults) {
            this.nextResults = nextResults;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

    }

}
