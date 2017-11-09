package com.example.hangtrantd.dacnpm.student;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 24/10/2017.
 */

public class TimeTableStudent {
    @SerializedName("thu")
    private String date;
    @SerializedName("namhoc")
    private String year;
    @SerializedName("hocky")
    private String semester;
    @SerializedName("tiet")
    private String lesson;
    @SerializedName("monhoc")
    private String subject;

    public TimeTableStudent(String date, String year, String semester, String lesson, String subject) {
        this.date = date;
        this.year = year;
        this.semester = semester;
        this.lesson = lesson;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
