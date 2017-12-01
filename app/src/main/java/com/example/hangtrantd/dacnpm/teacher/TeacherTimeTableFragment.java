package com.example.hangtrantd.dacnpm.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.hangtrantd.dacnpm.util.Semester;
import com.example.hangtrantd.dacnpm.util.Year;

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
        Api.getApiService().getYears().enqueue(new Callback<List<Year>>() {
            @Override
            public void onResponse(@NonNull Call<List<Year>> call, @NonNull Response<List<Year>> response) {
                List<Year> years = response.body();
                List<String> yearNames = new ArrayList<>();
                if (years != null) {
                    for (int i = 0; i < years.size(); i++) {
                        yearNames.add("Năm học "+years.get(i).getTen());
                    }
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearNames);
                    mSpinnerYear.setAdapter(adapterYear);
                    mSpinnerYear.setSelection(adapterYear.getPosition( "Năm học "+mCalendar.get(Calendar.YEAR) + "-" + (mCalendar.get(Calendar.YEAR) + 1)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Year>> call, @NonNull Throwable t) {
            }
        });

        mSpinnerSemester = mView.findViewById(R.id.spinnerSemesterTimeTable);
        mSpinnerSemester.setOnItemSelectedListener(this);
        Api.getApiService().getSemesters().enqueue(new Callback<List<Semester>>() {
            @Override
            public void onResponse(@NonNull Call<List<Semester>> call, @NonNull Response<List<Semester>> response) {
                List<Semester> semesters = response.body();
                List<String> semesterNames = new ArrayList<>();
                if (semesters != null) {
                    for (int i = 0; i < semesters.size(); i++) {
                        semesterNames.add(semesters.get(i).getTen());
                    }
                    ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, semesterNames);
                    mSpinnerSemester.setAdapter(adapterSemester);
                    switch (mCalendar.get(Calendar.MONTH)) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            mSemester = "Học Kì 1";
                            break;
                        default:
                            mSemester = "Học Kì 2";
                            break;
                    }
                    mSpinnerSemester.setSelection(adapterSemester.getPosition(mSemester));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Semester>> call, @NonNull Throwable t) {
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
                        if (("Năm học "+mTimeTableTeachers.get(j).getYear()).equals(mYear)) {
                            if (mTimeTableTeachers.get(j).getSemester().equals(mSemester)) {
                                switch (mTimeTableTeachers.get(j).getDate()) {
                                    case "2":
                                        int x = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 2;
                                        mTeacherTimeTables.set(x, mTimeTableTeachers.get(j));
                                        break;
                                    case "3":
                                        int y = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 3;
                                        mTeacherTimeTables.set(y, mTimeTableTeachers.get(j));
                                        break;
                                    case "4":
                                        int z = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 4;
                                        mTeacherTimeTables.set(z, mTimeTableTeachers.get(j));
                                        break;
                                    case "5":
                                        int m = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 5;
                                        mTeacherTimeTables.set(m, mTimeTableTeachers.get(j));
                                        break;
                                    case "6":
                                        int n = Integer.parseInt(mTimeTableTeachers.get(j).getLesson()) * 5 + 6;
                                        mTeacherTimeTables.set(n, mTimeTableTeachers.get(j));
                                        break;
                                }
                            }
                        }
                    }
                    initTimeTable();
                    mClazzSubjects.clear();
                    for (int i = 0; i < mTeacherTimeTables.size(); i++) {
                        mClazzSubjects.add(new ClassSubject(mTeacherTimeTables.get(i).getClazz(),mTeacherTimeTables.get(i).getSubject()));
                    }
                    mName.clear();
                    for (int i = 0; i < mClazzSubjects.size(); i++) {
                        mName.add(mClazzSubjects.get(i).getSubject()+"\n"+mClazzSubjects.get(i).getClazz());
                    }
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
