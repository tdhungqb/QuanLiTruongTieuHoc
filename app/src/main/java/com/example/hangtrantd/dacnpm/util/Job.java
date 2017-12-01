package com.example.hangtrantd.dacnpm.util;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 30/11/2017.
 */

public class Job {

    @SerializedName("id")
    private String id;
    @SerializedName("ten")
    private String name;

    public Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
