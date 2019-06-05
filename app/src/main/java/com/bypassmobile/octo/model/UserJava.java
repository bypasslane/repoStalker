package com.bypassmobile.octo.model;


import com.google.gson.annotations.SerializedName;

public class UserJava {

    @SerializedName("login")
    private final String name;

    @SerializedName("avatar_url")
    private final String profileURL;

    public UserJava(String name, String profileURL) {
        this.name = name;
        this.profileURL = profileURL;
    }

    public String getName() {
        return name;
    }

    public String getProfileURL() {
        return profileURL;
    }
}
