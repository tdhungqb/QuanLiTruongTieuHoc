package com.example.hangtrantd.dacnpm.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 12/10/2017.
 */

public class User implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("maso")
    private String maso;
    @SerializedName("password")
    private String password;
    @SerializedName("permission")
    private int permission;

    private User(Parcel in) {
        maso = in.readString();
        password = in.readString();
        permission = in.readInt();
    }

    public User(String maso, String password, int permission) {
        this.maso = maso;
        this.password = password;
        this.permission = permission;
    }

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

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(maso);
        parcel.writeString(password);
        parcel.writeInt(permission);
    }
}
