package com.example.hangtrantd.dacnpm.teacher;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 23/10/2017.
 */

public class NameStudent {
    @SerializedName("idHS")
    private String id;
    @SerializedName("tenHS")
    private String name;
    @SerializedName("namhoc")
    private String year;
    @SerializedName("lop")
    private String clazz;

    public NameStudent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
