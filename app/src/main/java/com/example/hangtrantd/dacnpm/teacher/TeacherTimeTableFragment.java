package com.example.hangtrantd.dacnpm.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.timetable.TimeTableAdapter;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hangtrantd.dacnpm.home.MainActivity.mIdUser;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 18/10/2017.
 */

public class TeacherTimeTableFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private View mView;
    private Spinner mSpinnerYear;
    private Spinner mSpinnerSemester;
    private String mYear;
    private String mSemester;
    private TimeTableAdapter mAdapter;
    private Calendar mCalendar = Calendar.getInstance();
    private GridView mGridView;
    private List<TimeTableTeacher> mTimeTableTeachers = new ArrayList<>();
    private List<TimeTableTeacher> mTeacherTimeTables = new ArrayList<>();
    private List<ClassSubject> mClazzSubjects = new ArrayList<>();
    private List<String> mName = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_time_table, container, false);
        initList();
        initViews();
        initSpinners();
        return mView;
    }


    private void initList() {
        for (int i = 0; i < 66; i++) {
            mTeacherTimeTables.add(new TimeTableTeacher("", "", "", "", "",""));
        }
    }

    private void initViews() {
        mGridView = mView.findViewById(R.id.gridViewTimeTable);
        mAdapter = new TimeTableAdapter(getActivity(), mName);
        mGridView.setAdapter(mAdapter);
    }

    private void initSpinners() {
        mSpinnerYear = mView.findViewById(R.id.spinnerYearTimeTable);
        mSpinnerYear.setOnItemSelectedListener(this);
        Api.getApiService().getYears().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> years = response.body();
                if (years != null) {
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, years);
                    mSpinnerYear.setAdapter(adapterYear);
                    int currentYear = mCalendar.get(Calendar.YEAR);
                    mSpinnerYear.setSelection(adapterYear.getPosition("Năm học " + currentYear + "-" + (currentYear + 1)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

            }
        });

        mSpinnerSemester = mView.findViewById(R.id.spinnerSemesterTimeTable);
        mSpinnerSemester.setOnItemSelectedListener(this);
        Api.getApiService().getSemesters().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> semesters = response.body();
                String currentSemester;
                if (semesters != null) {
                    ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, semesters);
                    mSpinnerSemester.setAdapter(adapterSemester);
                    switch (mCalendar.get(Calendar.MONTH)) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            currentSemester = "Học kì 1";
                            break;
                        default:
                            currentSemester = "Học kì 2";
                            break;
                    }
                    mSpinnerSemester.setSelection(adapterSemester.getPosition(currentSemester));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerYearTimeTable:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
            case R.id.spinnerSemesterTimeTable:
                mSemester = mSpinnerSemester.getSelectedItem().toString().substring(7, 8);
                break;
        }
        for (int j = 0; j < 66; j++) {
            mTeacherTimeTables.set(j, new TimeTableTeacher("", "", "", "", "",""));
        }
        getDataTeacher();
    }

    private void getDataTeacher() {
        Api.getApiService().getTimeTableOfTeacher(mIdUser).enqueue(new Callback<List<TimeTableTeacher>>() {
            @Override
            public void onResponse(@NonNull Call<List<TimeTableTeacher>> call, @NonNull Response<List<TimeTableTeacher>> response) {
                mTimeTableTeachers = response.body();
                if (mTimeTableTeachers != null) {
                    for (int j = 0; j < mTimeTableTeachers.size(); j++) {
                        Log.d("sssssssssss20",mYear+","+mSemester);
                        Log.d("sssssssssss20",mTimeTableTeachers.get(j).getYear()+","+mTimeTableTeachers.get(j).getSemester());
                        if (mTimeTableTeachers.get(j).getYear().equals(mYear)) {
                            if (mTimeTableTeachers.get(j).getSemester().equals(mSemester)) {
                                switch (mTimeTableTeachers.get(j).getDate()) {
                                    case "2":
                                        Log.d("sssssssssss2",mTeacherTimeTables.get(0).getClazz()+","+mIdUser);
                                        int x = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 2;
                                        mTeacherTimeTables.set(x, mTimeTableTeachers.get(j));
                                        break;
                                    case "3":
                                        Log.d("sssssssssss3",mTeacherTimeTables.get(0).getClazz()+","+mIdUser);
                                        int y = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 3;
                                        mTeacherTimeTables.set(y, mTimeTableTeachers.get(j));
                                        break;
                                    case "4":
                                        Log.d("sssssssssss4",mTeacherTimeTables.get(0).getClazz()+","+mIdUser);
                                        int z = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 4;
                                        mTeacherTimeTables.set(z, mTimeTableTeachers.get(j));
                                        break;
                                    case "5":
                                        Log.d("ssssssssss5",mTeacherTimeTables.get(0).getClazz()+","+mIdUser);
                                        int m = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 5;
                                        mTeacherTimeTables.set(m, mTimeTableTeachers.get(j));
                                        break;
                                    case "6":
                                        int n = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 6;
                                        mTeacherTimeTables.set(n, mTimeTableTeachers.get(j));
                                        Log.d("sssssssssss6",mTeacherTimeTables.get(n).getSubject()+","+mIdUser);
                                        break;
                                }
                            }
                        }
                    }
                    initTimeTable();
                    mClazzSubjects.clear();
                    Log.d("sssssssssssh1",mTeacherTimeTables.size()+"");
                    for (int i = 0; i < mTeacherTimeTables.size(); i++) {
                        mClazzSubjects.add(new ClassSubject(mTeacherTimeTables.get(i).getClazz(),mTeacherTimeTables.get(i).getSubject()));
                    }
                    Log.d("sssssssssssh2",mClazzSubjects.size()+"");
                    mName.clear();
                    for (int i = 0; i < mClazzSubjects.size(); i++) {
                        mName.add(mClazzSubjects.get(i).getSubject()+"\n"+mClazzSubjects.get(i).getClazz());
                    }
                    Log.d("sssssssssssh3",mName.size()+"");
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TimeTableTeacher>> call, @NonNull Throwable t) {
            }
        });
    }

    private void initTimeTable() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                mTeacherTimeTables.set(i, new TimeTableTeacher("", "", "", "", "",""));
            } else {
                mTeacherTimeTables.set(i, new TimeTableTeacher("", "", "", "","", "Thứ " + String.valueOf(i + 1)));
            }
        }

        for (int i = 6, j = 1; i <= 60; i = i + 6, j++) {
            mTeacherTimeTables.set(i, new TimeTableTeacher("", "", "", "", "","Tiết " + String.valueOf(i - (5 * j))));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
