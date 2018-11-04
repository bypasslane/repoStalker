package com.bypassmobile.octo.model;


import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    private final String name;

    @SerializedName("avatar_url")
    private final String profileURL;

    @SerializedName("id")
    private final String userId;

    public User(String name, String profileURL, String userId) {
        this.name = name;
        this.userId = userId;
        this.profileURL = profileURL;
    }

    public String getName() {
        return name;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public String getUserId() { return userId; }
}
