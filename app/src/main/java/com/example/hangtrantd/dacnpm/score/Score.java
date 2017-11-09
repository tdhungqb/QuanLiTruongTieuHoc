package com.example.hangtrantd.dacnpm.score;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 24/10/2017.
 */

public class Score {
    @SerializedName("maHS")
    private String idStudent;
    @SerializedName("maMonhoc")
    private String idSubject;
    @SerializedName("tenMonhoc")
    private String nameSubject;
    @SerializedName("heso")
    private String factor;
    @SerializedName("hocki")
    private String semester;
    @SerializedName("namhoc")
    private String year;
    @SerializedName("lop")
    private String clazz;
    @SerializedName("mieng")
    private String mouth;
    @SerializedName("giuaki")
    private String midSemester;
    @SerializedName("cuoiki")
    private String finalSemester;

    public Score(String idStudent, String nameSubject, String factor, String semester, String year, String clazz, String mouth, String midSemester, String finalSemester) {
        this.idStudent = idStudent;
        this.nameSubject = nameSubject;
        this.factor = factor;
        this.semester = semester;
        this.year = year;
        this.clazz = clazz;
        this.mouth = mouth;
        this.midSemester = midSemester;
        this.finalSemester = finalSemester;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getMidSemester() {
        return midSemester;
    }

    public void setMidSemester(String midSemester) {
        this.midSemester = midSemester;
    }

    public String getFinalSemester() {
        return finalSemester;
    }

    public void setFinalSemester(String finalSemester) {
        this.finalSemester = finalSemester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
