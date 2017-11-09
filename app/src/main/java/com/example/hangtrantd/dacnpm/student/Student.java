package com.example.hangtrantd.dacnpm.student;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 12/10/2017.
 */

public class Student {
    @SerializedName("id")
    private String id;
    @SerializedName("hoten")
    private String name;
    @SerializedName("gioitinh")
    private String gender;
    @SerializedName("ngaysinh")
    private String birthDay;
    @SerializedName("noisinh")
    private String address;
    @SerializedName("lop")
    private String clazz;
    @SerializedName("dantoc")
    private String nation;
    @SerializedName("tongiao")
    private String religion;
    @SerializedName("hotencha")
    private String fatherName;
    @SerializedName("nghenghiepcha")
    private String fatherJob;
    @SerializedName("hotenme")
    private String motherName;
    @SerializedName("nghenghiepme")
    private String motherJob;
    @SerializedName("sodienthoai")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherJob() {
        return fatherJob;
    }

    public void setFatherJob(String fatherJob) {
        this.fatherJob = fatherJob;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherJob() {
        return motherJob;
    }

    public void setMotherJob(String motherJob) {
        this.motherJob = motherJob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

