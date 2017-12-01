package com.example.hangtrantd.dacnpm.util;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 29/11/2017.
 */

public class Semester {
    @SerializedName("id")
    private String id;
    @SerializedName("ten")
    private String ten;
    @SerializedName("heso")
    private String factor;
    @SerializedName("sotuan")
    private String weekNumber;

    public Semester(String ten) {
        this.ten = ten;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }
}
