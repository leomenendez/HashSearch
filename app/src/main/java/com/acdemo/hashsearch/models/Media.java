package com.acdemo.hashsearch.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by leo on 1/14/17.
 */

@Table(name = "Media")
public class Media extends Model implements Serializable {

    public Media(){
        super();
    }

    @Column(name="MediaId")
    @SerializedName("id")
    private long mediaID;

    @Column(name="MediaUrl")
    @SerializedName("media_url")
    private String mediaUrl;

    @Column(name="TweetUrl")
    @SerializedName("url")
    private String url;

    @Column(name="DisplayUrl")
    @SerializedName("display_url")
    private String displayUrl;

    @Column(name="Type")
    @SerializedName("type")
    private String type;

    @Column(name="Sizes")
    private Sizes sizes;

    @Column(name = "TweetEntity")
    private Tweets.TweetEntities tweetEntity;


    public void saveAll(){
        sizes.saveAll();
        save();
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public long getMediaID() {
        return mediaID;
    }

    public void setMediaID(long mediaID) {
        this.mediaID = mediaID;
    }

    public Tweets.TweetEntities getTweetEntity() {
        return tweetEntity;
    }

    public void setTweetEntity(Tweets.TweetEntities tweetEntity) {
        this.tweetEntity = tweetEntity;
    }


    @Table(name = "Sizes")
    public static class Sizes extends Model implements Serializable {

        public Sizes(){
            super();
        }

        @Column(name="Medium")
        @SerializedName("medium")
        private Size medium;

        @Column(name="Thumb")
        @SerializedName("thumb")
        private Size thumb;

        @Column(name="Small")
        @SerializedName("small")
        private Size small;

        @Column(name="Large")
        @SerializedName("large")
        private Size large;

        public void saveAll(){
            medium.save();
            thumb.save();
            small.save();
            large.save();

            save();
        }

        public Size getLarge() {
            return large;
        }

        public void setLarge(Size large) {
            this.large = large;
        }

        public Size getSmall() {
            return small;
        }

        public void setSmall(Size small) {
            this.small = small;
        }

        public Size getThumb() {
            return thumb;
        }

        public void setThumb(Size thumb) {
            this.thumb = thumb;
        }

        public Size getMedium() {
            return medium;
        }

        public void setMedium(Size medium) {
            this.medium = medium;
        }


        @Table(name = "Size")
        public static class Size extends Model implements Serializable{

            public Size(){
                super();
            }

            @Column(name = "Width")
            @SerializedName("w")
            private int width;

            @Column(name = "Height")
            @SerializedName("h")
            private int height;

            @Column(name = "Resize")
            @SerializedName("resize")
            private String resize;

            public String getResize() {
                return resize;
            }

            public void setResize(String resize) {
                this.resize = resize;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }
    }
}
