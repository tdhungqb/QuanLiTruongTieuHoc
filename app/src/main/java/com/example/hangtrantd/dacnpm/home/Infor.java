package com.example.hangtrantd.dacnpm.home;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 09/10/2017.
 */

public class Infor {
    @SerializedName("id")
    private int id;
    @SerializedName("ten")
    private String title;
    @SerializedName("ngay")
    private String date;
    @SerializedName("mota")
    private String content;
    @SerializedName("hinh")
    private String image;

    public Infor(int id, String title, String date,String content, String image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
