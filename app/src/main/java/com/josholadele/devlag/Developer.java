package com.josholadele.devlag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oladele on 3/9/17.
 */

public class Developer implements Parcelable {
    private String username;
    private String photoUrl;
    private String profileUrl;

    protected Developer(Parcel in) {
        username = in.readString();
        photoUrl = in.readString();
        profileUrl = in.readString();
    }

    public Developer(){

    }
    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel in) {
            return new Developer(in);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(photoUrl);
        parcel.writeString(profileUrl);
    }
}
