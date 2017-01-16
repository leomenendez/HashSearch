package com.acdemo.hashsearch.network;

import com.acdemo.hashsearch.models.AuthenticationResponse;
import com.acdemo.hashsearch.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by leo on 1/13/17.
 */

public interface WebServiceConnection {

    @POST("/oauth2/token/")
    @FormUrlEncoded
    Call<AuthenticationResponse> getAuthToken(@Field("grant_type") String field);

    @GET("/1.1/search/tweets.json")
    Call<SearchResponse> getTweetSearchResults(@Query("q") String query,
                               @Query("count") int count);
}
