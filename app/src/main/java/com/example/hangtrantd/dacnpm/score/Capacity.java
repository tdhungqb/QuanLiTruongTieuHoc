package com.example.hangtrantd.dacnpm.score;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 02/11/2017.
 */

public class Capacity {

    @SerializedName("id")
    private String id;
    @SerializedName("ten")
    private String name;
    @SerializedName("diemcantren")
    private String above;
    @SerializedName("diemcanduoi")
    private String bellow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbove() {
        return above;
    }

    public void setAbove(String above) {
        this.above = above;
    }

    public String getBellow() {
        return bellow;
    }

    public void setBellow(String bellow) {
        this.bellow = bellow;
    }
}
