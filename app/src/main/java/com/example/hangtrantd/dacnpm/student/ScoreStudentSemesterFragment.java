package com.example.hangtrantd.dacnpm.student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Semester;
import com.example.hangtrantd.dacnpm.util.Year;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 07/11/2017.
 */

public class ScoreStudentSemesterFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private RecyclerView mRecyclerView;
    private ScoreStudentSemesterAdapter mAdapter;
    private Spinner mSpinnerSemester;
    private Spinner mSpinnerYear;
    private List<Score> mScores = new ArrayList<>();
    private Calendar mCalendar = Calendar.getInstance();
    private View mView;
    private String mYear;
    private String mSemester;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_student_score_semester, container, false);
        initViews();
        initSpinners();
        return mView;
    }

    private void initViews() {
        mRecyclerView = mView.findViewById(R.id.recyclerViewStudentScore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ScoreStudentSemesterAdapter(mScores);
        mRecyclerView.setAdapter(mAdapter);
        mSpinnerSemester = mView.findViewById(R.id.spinnerSemesterScore);
        mSpinnerYear = mView.findViewById(R.id.spinnerYearScore);
    }

    private void initSpinners() {
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

        mSpinnerYear.setOnItemSelectedListener(this);
        Api.getApiService().getYears().enqueue(new Callback<List<Year>>() {
            @Override
            public void onResponse(@NonNull Call<List<Year>> call, @NonNull Response<List<Year>> response) {
                List<Year> years = response.body();
                List<String> yearNames = new ArrayList<>();
                if (years != null) {
                    for (int i = 0; i < years.size(); i++) {
                        yearNames.add("Năm học " + years.get(i).getTen());
                    }
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearNames);
                    mSpinnerYear.setAdapter(adapterYear);
                    mSpinnerYear.setSelection(adapterYear.getPosition("Năm học " + mCalendar.get(Calendar.YEAR) + "-" + (mCalendar.get(Calendar.YEAR) + 1)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Year>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerSemesterScore:
                mSemester = mSpinnerSemester.getSelectedItem().toString();
                break;
            case R.id.spinnerYearScore:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
        }
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getData() {
        Api.getApiService().getScores(MainActivity.mIdUser).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                List<Score> scores = response.body();
                if (scores != null) {
                    mScores.clear();
                    for (int i = 0; i < scores.size(); i++) {
                        if (("Năm học "+scores.get(i).getYear()).equals(mYear) && scores.get(i).getSemester().equals(mSemester)) {
                            mScores.add(scores.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {

            }
        });
    }
}
