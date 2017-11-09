package com.example.hangtrantd.dacnpm.teacher;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 13/10/2017.
 */

public class Teacher {
    @SerializedName("id")
    private String id;
    @SerializedName("hoten")
    private String name;
    @SerializedName("diachi")
    private String address;
    @SerializedName("sodienthoai")
    private String phone;
    @SerializedName("loaimonhoc_id")
    private String typeCourse;
    @SerializedName("gioitinh")
    private String gender;
    @SerializedName("ngaysinh")
    private String birthDay;
    @SerializedName("tongiao")
    private String religion;
    @SerializedName("dantoc")
    private String nation;

    public Teacher(String id, String name, String address, String phone, String gender, String birthDay, String religion, String nation) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.typeCourse = typeCourse;
        this.gender = gender;
        this.birthDay = birthDay;
        this.religion = religion;
        this.nation = nation;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeCourse() {
        return typeCourse;
    }

    public void setTypeCourse(String typeCourse) {
        this.typeCourse = typeCourse;
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

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
