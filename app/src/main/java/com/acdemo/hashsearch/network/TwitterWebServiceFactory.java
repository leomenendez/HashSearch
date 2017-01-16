package com.acdemo.hashsearch.network;

import android.util.Base64;
import android.util.Log;

import com.acdemo.hashsearch.HashApplication;
import com.acdemo.hashsearch.models.AuthenticationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.twitter.sdk.android.core.Callback;
//import com.twitter.sdk.android.core.Result;
//import com.twitter.sdk.android.core.TwitterException;
//import io.fabric.sdk.android.services.network.HttpRequest;

/**
 * Created by leo on 1/13/17.
 */

public class TwitterWebServiceFactory {
    public static final String TAG = TwitterWebServiceFactory.class.getSimpleName();

    public static final String TWITTER_URL = "https://api.twitter.com/";
    private static String authID = null;
    private static String authType = "Basic";

    private static WebServiceConnection serviceConnection;

    public static WebServiceConnection getInstance(){
        if(serviceConnection == null){
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new HeaderInterceptor())
                    .build();
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.TRANSIENT)
                    .create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TWITTER_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            serviceConnection = retrofit.create(WebServiceConnection.class);


//            this is init of service serviceConnection
            if(authID==null){//need to get auth token for future requests
                String twitterKeys = HashApplication.TWITTER_KEY+":"+HashApplication.TWITTER_SECRET;
                authID = Base64.encodeToString(twitterKeys.getBytes(), Base64.NO_WRAP);
                Call<AuthenticationResponse> call = serviceConnection.getAuthToken("client_credentials");
                call.enqueue(new Callback<AuthenticationResponse>() {

                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, retrofit2.Response<AuthenticationResponse> response) {
                        authID = response.body().getAccessToken();
                        authType = response.body().getTokenType();

                    }

                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        authID = null;
                        Log.d(TAG, t.getMessage());
                    }
                });





            }
        }
        return serviceConnection;
    }

    private static class HeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Authorization", authType+" "+authID)
                    .build();
            Response response = chain.proceed(request);
            return response;

        }
    }
}
