package com.example.hangtrantd.dacnpm.student;

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

public class StudentTimeTableFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private View mView;
    private Spinner mSpinnerYear;
    private Spinner mSpinnerSemester;
    private String mYear;
    private String mSemester;
    private TimeTableAdapter mAdapter;
    private Calendar mCalendar = Calendar.getInstance();
    private GridView mGridView;
    private List<TimeTableStudent> mTimeTableStudents = new ArrayList<>();
    private List<TimeTableStudent> mStudentTimeTables = new ArrayList<>();
    private List<String> mSubjects = new ArrayList<>();

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
            mStudentTimeTables.add(new TimeTableStudent("", "", "", "", ""));
        }
    }

    private void initViews() {
        mGridView = mView.findViewById(R.id.gridViewTimeTable);
        mAdapter = new TimeTableAdapter(getActivity(), mSubjects);
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
            mStudentTimeTables.set(j, new TimeTableStudent("", "", "", "", ""));
        }
        getDataStudent();
    }

    private void getDataStudent() {
        Api.getApiService().getTimeTableStudent(mIdUser).enqueue(new Callback<List<TimeTableStudent>>() {
            @Override
            public void onResponse(@NonNull Call<List<TimeTableStudent>> call, @NonNull Response<List<TimeTableStudent>> response) {
                mTimeTableStudents = response.body();
                if (mTimeTableStudents != null) {
                    for (int j = 0; j < mTimeTableStudents.size(); j++) {
                        if (mTimeTableStudents.get(j).getYear().equals(mYear)) {
                            if (mTimeTableStudents.get(j).getSemester().equals(mSemester)) {
                                switch (mTimeTableStudents.get(j).getDate()) {
                                    case "2":
                                        int x = Integer.parseInt(mTimeTableStudents.get(j).getLesson()) * 5 + 2;
                                        mStudentTimeTables.set(x, mTimeTableStudents.get(j));
                                        break;
                                    case "3":
                                        int y = Integer.parseInt(mTimeTableStudents.get(j).getLesson()) * 5 + 3;
                                        mStudentTimeTables.set(y, mTimeTableStudents.get(j));
                                        break;
                                    case "4":
                                        int z = Integer.parseInt(mTimeTableStudents.get(j).getLesson()) * 5 + 4;
                                        mStudentTimeTables.set(z, mTimeTableStudents.get(j));
                                        break;
                                    case "5":
                                        int m = Integer.parseInt(mTimeTableStudents.get(j).getLesson()) * 5 + 5;
                                        mStudentTimeTables.set(m, mTimeTableStudents.get(j));
                                        break;
                                    case "6":
                                        int n = Integer.parseInt(mTimeTableStudents.get(j).getLesson()) * 5 + 6;
                                        mStudentTimeTables.set(n, mTimeTableStudents.get(j));
                                        break;
                                }
                            }
                        }
                    }
                    initTimeTable();
                    mSubjects.clear();
                    for (int i = 0; i < mStudentTimeTables.size(); i++) {
                        mSubjects.add(mStudentTimeTables.get(i).getSubject());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TimeTableStudent>> call, @NonNull Throwable t) {
            }
        });
    }

    private void initTimeTable() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                mStudentTimeTables.set(i, new TimeTableStudent("", "", "", "", ""));
            } else {
                mStudentTimeTables.set(i, new TimeTableStudent("", "", "", "", "Thứ " + String.valueOf(i + 1)));
            }
        }

        for (int i = 6, j = 1; i <= 60; i = i + 6, j++) {
            mStudentTimeTables.set(i, new TimeTableStudent("", "", "", "", "Tiết " + String.valueOf(i - (5 * j))));

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
