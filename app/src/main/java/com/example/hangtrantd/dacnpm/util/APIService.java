package com.example.hangtrantd.dacnpm.util;

import com.example.hangtrantd.dacnpm.conduct.Conduct;
import com.example.hangtrantd.dacnpm.home.Infor;
import com.example.hangtrantd.dacnpm.login.User;
import com.example.hangtrantd.dacnpm.score.Capacity;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.student.TimeTableStudent;
import com.example.hangtrantd.dacnpm.teacher.NameStudent;
import com.example.hangtrantd.dacnpm.teacher.Subject;
import com.example.hangtrantd.dacnpm.teacher.Teacher;
import com.example.hangtrantd.dacnpm.teacher.TimeTableTeacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 16/10/2017.
 */

public interface APIService {

    @GET("nguoidung/{maso}")
    Call<List<User>> getInforUser(@Path("maso") String id);

    @GET("giaovien/getTeacher/{id}")
    Call<List<Teacher>> getInforTeacher(@Path("id") String id);

    @GET("giaovien/editTeacher/{id}/{loaimonhoc}/{diachi}/{sodienthoai}'")
    Call<String> updateTeacher(@Path("id") String id, @Path("loaimonhoc") String religion, @Path("diachi") String address, @Path("sodienthoai") String phone);

    @GET("hoatdong")
    Call<List<Infor>> getInfors();

    @GET("nghenghiep")
    Call<List<Job>> getJobs();

    @GET("hocsinh/getStudent/{id}")
    Call<List<Student>> getInforStudent(@Path("id") String id);

    @GET("hocsinh/editStudent/{id}/{diachi}/{dantoc}/{tongiao}/{nghenghiepcha}/{nghenghiepme}/{sodienthoai}")
    Call<String> updateStudent(@Path("id") String id,
                               @Path("diachi") String address,
                               @Path("dantoc") String nation, @Path("tongiao") String religion,
                               @Path("nghenghiepcha") String fatherJob,
                               @Path("nghenghiepme") String motherJob, @Path("sodienthoai") String phone);

    @GET("giaovien/getScheduleOfTeacher/{id}")
    Call<List<TimeTableTeacher>> getTimeTableOfTeacher(@Path("id") String id);

    @GET("hocsinh/getScheduleOfStudent/{id}") //29
    Call<List<TimeTableStudent>> getTimeTableStudent(@Path("id") String id);


    @GET("giaovien/getClassOfTeacher/{id}")
    Call<List<ClassName>> getClasses(@Path("id") String id);

//    @GET("lop")
//    Call<List<Clazz>> getClasses();

    @GET("giaovien/getStudents/{id}")
    Call<List<NameStudent>> getStudents(@Path("id") String id);

    @GET("monhoc")
    Call<List<Subject>> getSubjects();

    @GET("hocsinh/getScoreStudent/{maHS}")
    Call<List<Score>> getScores(@Path("maHS") String idStudent);

    @GET("giaovien/updateScore/{idHS}/{tenMonHoc}/{lop}/{hocky}/{namhoc}/{heso1}/{heso2}/{heso3}")
    Call<String> updateScore(@Path("idHS") String idStudent, @Path("tenMonHoc") String nameSubject,
                             @Path("lop") String clazz, @Path("hocky") String semester,
                             @Path("namhoc") String year, @Path("heso1") String mouth,
                             @Path("heso2") String midSemester, @Path("heso3") String finalSemester);

//    @POST("/updateAvatar.php")
//    @FormUrlEncoded
//    Call<String> updateAvatar(@Field("id") String id, @Field("base64") String data);

//    @GET("/getAvatar.php/{id}")
//    Call<String> getAvatar(@Path("id") String id);

    @GET("dantoc")
    Call<List<Country>> getNations();

    @GET("tongiao")
    Call<List<Country>> getReligions();

    @GET("namhoc")
    Call<List<Year>> getYears();

    @GET("hocky")
    Call<List<Semester>> getSemesters();

    @GET("hocluc")
    Call<List<Capacity>> getCapacities();

    @GET("hanhkiem")
    Call<List<NameConduct>> getConducts();

    @GET("loaimonhoc")
    Call<List<SubjectType>> getSubjectTypes();

    @GET("hocsinh/getConductOfStudent/{id}")
    Call<List<Conduct>> getConduct(@Path("id") String id);

    @GET("giaovien/updateConduct/{id}/{hanhkiem}/{namhoc}/{hocky}")
    Call<String> updateConduct(@Path("id") String id, @Path("hanhkiem") String conduct, @Path("namhoc") String year, @Path("hocky") String hocky);

    @GET("/getProvences.php")
    Call<List<String>> getProvences();

    @GET("/getDistricts.php/{tinh}")
    Call<List<String>> getDistricts(@Path("tinh") String id);

    @GET("/getDistrictName.php/{id}")
    Call<MyAdrress> getDistrictName(@Field("id") String id);
}

