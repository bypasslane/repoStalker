package com.bypassmobile.octo;

import android.app.Activity;
import android.os.Bundle;

import com.bypassmobile.octo.rest.GithubEndpoint;

import retrofit.RestAdapter;

public class BaseActivity extends Activity {

    private GithubEndpoint endpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer(GithubEndpoint.SERVER)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        endpoint = adapter.create(GithubEndpoint.class);
    }

    public GithubEndpoint getEndpoint() {
        return endpoint;
    }
}
