package com.example.hangtrantd.dacnpm.util;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 29/11/2017.
 */

public class Clazz {
    @SerializedName("ten")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("khoilop_id")
    private String khoi;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clazz(String name) {
        this.name = name;
    }
}
