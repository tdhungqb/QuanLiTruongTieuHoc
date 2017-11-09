package com.example.hangtrantd.dacnpm.teacher;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 20/10/2017.
 */

public class TimeTableTeacher {
    @SerializedName("thu")
    private String date;
    @SerializedName("namhoc")
    private String year;
    @SerializedName("hocky")
    private String semester;
    @SerializedName("tiet")
    private String lesson;
    @SerializedName("lop")
    private String clazz;
    @SerializedName("monhoc")
    private String subject;

    public TimeTableTeacher(String date, String year, String semester, String lesson, String clazz, String subject) {
        this.date = date;
        this.year = year;
        this.semester = semester;
        this.lesson = lesson;
        this.clazz = clazz;
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
