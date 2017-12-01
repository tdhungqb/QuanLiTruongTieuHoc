package com.example.hangtrantd.dacnpm.util;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 22/11/2017.
 */

public class MyAdrress {
    @SerializedName("tinh")
    private String provence;
    @SerializedName("huyen")
    private String district;

    public MyAdrress(String provence, String district) {
        this.provence = provence;
        this.district = district;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
