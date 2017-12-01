package com.example.hangtrantd.dacnpm.util;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 27/10/2017.
 */

public class Country {
    @SerializedName("id")
    private String id;
    @SerializedName("ten")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
