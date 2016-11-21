package com.bypassmobile.octo.core.rest;


import com.bypassmobile.octo.core.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GithubEndpoint {

    String SERVER = "https://api.github.com";

    @GET("/users/{username}")
    void getUser(@Path("username") String user, Callback<User> callback);

    @GET("/users/{username}/following")
    void getUsersFollowedBy(@Path("username") String user, Callback<List<User>> callback);

    @GET("/orgs/{id}/members")
    void getUsersInOrganization(@Path("id") String organization, Callback<List<User>> callback);
}
