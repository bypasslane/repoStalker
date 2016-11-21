package com.bypassmobile.octo.core.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    private final Long mId;

    @SerializedName("login")
    private final String mName;

    @SerializedName("avatar_url")
    private final String mAvatarURL;

    @SerializedName("followers")
    private final Integer mFollowerCount;

    public User(Long mId, String mName, String avatarUrl, Integer followerCount) {
        this.mId = mId;
        this.mName = mName;
        this.mAvatarURL = avatarUrl;
        this.mFollowerCount = followerCount;
    }

    protected User(Parcel in) {
        mId = in.readLong();
        mName = in.readString();
        mAvatarURL = in.readString();
        mFollowerCount = in.readInt();
    }

    public String getName() {
        return mName;
    }

    public String getProfileURL() {
        return mAvatarURL;
    }

    public Long getId() {
        return mId;
    }

    public Integer getFollowerCount() {
        return mFollowerCount;
    }



// ====================================================================================== PARCELABLE

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mName);
        parcel.writeString(mAvatarURL);
        parcel.writeInt(mFollowerCount);
    }
}
