package com.acdemo.hashsearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leo on 1/13/17.
 */

public class SearchQuery {

    @SerializedName("q")
    private String query;

    @SerializedName("count")
    private int count;

}
