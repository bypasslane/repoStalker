package com.bypassmobile.octo.features;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bypassmobile.octo.rest.GithubEndpoint;
import com.bypassmobile.octo.utils.AppInfo;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends Activity {

    private GithubEndpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppInfo.GITHUB_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        endpoint = retrofit.create(GithubEndpoint.class);
    }

    public GithubEndpoint getEndpoint() {
        return endpoint;
    }
}