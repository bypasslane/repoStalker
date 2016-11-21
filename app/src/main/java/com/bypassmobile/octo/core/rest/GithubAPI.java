package com.bypassmobile.octo.core.rest;

import retrofit.RestAdapter;

/**
 * James (Impactable)
 */

public class GithubAPI {
    private static RestAdapter REST_ADAPTER = new RestAdapter.Builder()
                                                    .setServer(GithubEndpoint.SERVER)
                                                    .setLogLevel(RestAdapter.LogLevel.FULL)
                                                    .build();

    public static GithubEndpoint getEndpoint() {
        return REST_ADAPTER.create(GithubEndpoint.class);
    }
}
