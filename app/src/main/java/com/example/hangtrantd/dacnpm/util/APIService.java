package com.example.hangtrantd.dacnpm.util;

import com.example.hangtrantd.dacnpm.conduct.Conduct;
import com.example.hangtrantd.dacnpm.home.Infor;
import com.example.hangtrantd.dacnpm.login.User;
import com.example.hangtrantd.dacnpm.score.Capacity;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.teacher.NameStudent;
import com.example.hangtrantd.dacnpm.teacher.Subject;
import com.example.hangtrantd.dacnpm.teacher.Teacher;
import com.example.hangtrantd.dacnpm.student.TimeTableStudent;
import com.example.hangtrantd.dacnpm.teacher.TimeTableTeacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 16/10/2017.
 */

public interface APIService {

    @POST("/user.php")
    @FormUrlEncoded
    Call<User> getInforUser(@Field("maso") String id);

    @POST("/getTeacher.php")
    @FormUrlEncoded
    Call<Teacher> getInforTeacher(@Field("id") String id);

    @POST("/updateTeacher.php")
    @FormUrlEncoded
    Call<String> updateTeacher(@Field("id") String id, @Field("hoten") String name, @Field("diachi") String address, @Field("sodienthoai") String phone, @Field("gioitinh") String gender, @Field("ngaysinh") String birthDay, @Field("tongiao") String religion, @Field("dantoc") String nation);

    @GET("/getInfors.php")
    Call<List<Infor>> getInfors();

    @POST("/getStudent.php")
    @FormUrlEncoded
    Call<Student> getInforStudent(@Field("id") String id);

    @POST("/updateStudent.php")
    @FormUrlEncoded
    Call<String> updateStudent(@Field("id") String id, @Field("hoten") String name, @Field("gioitinh") String gender,
                               @Field("ngaysinh") String birthDay, @Field("noisinh") String address, @Field("lop") String clazz,
                               @Field("dantoc") String nation, @Field("tongiao") String religion,
                               @Field("hotencha") String fatherName, @Field("nghenghiepcha") String fatherJob,
                               @Field("hotenme") String motherName, @Field("nghenghiepme") String motherJob, @Field("sodienthoai") String phone);

    @POST("/getTimeTableOfTeacher.php")
    @FormUrlEncoded
    Call<List<TimeTableTeacher>> getTimeTableOfTeacher(@Field("id") String id);

    @POST("/showTimeTableStudent.php")
    @FormUrlEncoded
    Call<List<TimeTableStudent>> getTimeTableStudent(@Field("id") String id);


    @POST("/getClassesOfTeacher.php")
    @FormUrlEncoded
    Call<List<String>> getClasses(@Field("id") String id);

    @GET("/getClasses.php")
    Call<List<String>> getClasses();

    @POST("/getStudents.php")
    @FormUrlEncoded
    Call<List<NameStudent>> getStudents(@Field("id") String id);

    @GET("/getSubjects.php")
    Call<List<Subject>> getSubjects();

    @POST("/scoreStudentOfTeacher.php")
    @FormUrlEncoded
    Call<List<Score>> getScores(@Field("maHS") String idStudent);

    @POST("/updateScore.php")
    @FormUrlEncoded
    Call<String> updateScore(@Field("idStudent") String idStudent, @Field("nameSubject") String nameSubject, @Field("class") String clazz, @Field("semester") String semester, @Field("year") String year,
                             @Field("mouth") String mouth,
                             @Field("midSemester") String midSemester, @Field("finalSemester") String finalSemester);

    @POST("/updateAvatar.php")
    @FormUrlEncoded
    Call<String> updateAvatar(@Field("id") String id, @Field("base64") String data);

    @POST("/getAvatar.php")
    @FormUrlEncoded
    Call<String> getAvatar(@Field("id") String id);

    @GET("/getNations.php")
    Call<List<Country>> getNations();

    @GET("/getReligions.php")
    Call<List<Country>> getReligions();

    @GET("/getYears.php")
    Call<List<String>> getYears();

    @GET("/getSemesters.php")
    Call<List<String>> getSemesters();

    @GET("/capacityOfStudent.php")
    Call<List<Capacity>> getCapacities();

    @GET("/getConducts.php")
    Call<List<String>> getConducts();

    @POST("/getConductStudent.php")
    @FormUrlEncoded
    Call<List<Conduct>> getConduct(@Field("id") String id);

    @POST("/updateConduct.php")
    @FormUrlEncoded
    Call<String> updateConduct(@Field("id") String id, @Field("hanhkiem") String conduct,@Field("namhoc") String year,@Field("hocky") String hocky,@Field("ghichu") String note);
}

