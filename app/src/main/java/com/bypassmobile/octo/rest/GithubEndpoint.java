package com.bypassmobile.octo.rest;


import com.bypassmobile.octo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubEndpoint {

    @GET("/users/{id}")
    public Call<List<User>> getUser(@Path("id") String user);

    @GET("/users/{id}/following")
    public Call<List<User>> getFollowingUser(@Path("id") String user);

    @GET("/orgs/{id}/members")
    public Call<List<User>> getOrganizationMember(@Path("id") String organization);
}
