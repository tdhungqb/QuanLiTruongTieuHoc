package com.example.hangtrantd.dacnpm.conduct;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 05/11/2017.
 */

public class Conduct {
    @SerializedName("hanhkiem")
    private String conduct;
    @SerializedName("namhoc")
    private String year;
    @SerializedName("hocky")
    private String semester;
    @SerializedName("lop")
    private String clazz;
    @SerializedName("Ghichu")
    private String note;

    public Conduct(String conduct, String year, String semester, String clazz, String note) {
        this.conduct = conduct;
        this.year = year;
        this.semester = semester;
        this.clazz = clazz;
        this.note = note;
    }

    public String getConduct() {
        return conduct;
    }

    public void setConduct(String conduct) {
        this.conduct = conduct;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
