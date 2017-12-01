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
    @SerializedName("loaimonhoc")
    private String typeCourse;
    @SerializedName("gioitinh")
    private String gender;
    @SerializedName("ngaysinh")
    private String birthDay;


    public Teacher(String id, String name, String address, String phone, String typeCourse, String gender, String birthDay) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.typeCourse = typeCourse;
        this.gender = gender;
        this.birthDay = birthDay;
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

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}

