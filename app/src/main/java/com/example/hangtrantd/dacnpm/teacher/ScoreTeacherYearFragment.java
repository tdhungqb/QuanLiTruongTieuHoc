package com.example.hangtrantd.dacnpm.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.score.Capacity;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.score.ScoreYear;
import com.example.hangtrantd.dacnpm.score.ScoreYearAdapter;
import com.example.hangtrantd.dacnpm.util.Api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 24/10/2017.
 */

public class ScoreTeacherYearFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ScoreYearAdapter mAdapter;
    private List<Score> mScores;
    private List<ScoreYear> mScoreFilters = new ArrayList<>();
    private Spinner mSpinnerYear;
    private Calendar mCalendar = Calendar.getInstance();
    private String mYear;
    private Float mScore = 0f;
    private View mView;
    private String mIdStudent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_score_year, container, false);
       Log.d("wwwwwwwwww","wwwwww");
        mRecyclerView = mView.findViewById(R.id.recyclerScore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initSpinner(mView);
        mAdapter = new ScoreYearAdapter(mScoreFilters);
        mRecyclerView.setAdapter(mAdapter);
        initViews(mView);
        mIdStudent = ScoreTeacherFragment.getIdStudent();
        setScore();
        return mView;
    }

    private void initSpinner(View view) {
        mSpinnerYear = view.findViewById(R.id.spinnerYearSummery);
        Api.getApiService().getYears().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> years = response.body();
                if (years != null) {
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, years);
                    mSpinnerYear.setAdapter(adapterYear);
                    mSpinnerYear.setSelection(adapterYear.getPosition("Năm học " + mCalendar.get(Calendar.YEAR) + "-" + (mCalendar.get(Calendar.YEAR) + 1)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

            }
        });
        mSpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mYear = mSpinnerYear.getSelectedItem().toString();
                mScoreFilters.clear();
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private void getData() {
        if (mIdStudent != null) {
            Api.getApiService().getScores(mIdStudent).enqueue(new Callback<List<Score>>() {
                @Override
                public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                    mScores = response.body();
                    if (mScores != null) {
                        setScore();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                }
            });
        }
    }

    public void setScore() {
        Api.getApiService().getScores(mIdStudent).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                if (response.body() != null) {
                    mScores = response.body();
                    mScoreFilters.clear();
                    for (int i = 0; i < mScores.size(); i++) {
                        if (mScores.get(i).getYear().equals(mYear)) {
                            if (i + 1 < mScores.size() && mScores.get(i).getNameSubject().equals(mScores.get(i + 1).getNameSubject())) {
                                String score = sumScore(mScores.get(i).getMouth(), mScores.get(i).getMidSemester(), mScores.get(i).getFinalSemester(),
                                        mScores.get(i + 1).getMouth(), mScores.get(i + 1).getMidSemester(), mScores.get(i + 1).getFinalSemester());
                                mScoreFilters.add(new ScoreYear(mScores.get(i).getNameSubject(), score, sumSemester(mScores.get(i).getMouth(), mScores.get(i).getMidSemester(), mScores.get(i).getFinalSemester()),
                                        sumSemester(mScores.get(i + 1).getMouth(), mScores.get(i + 1).getMidSemester(), mScores.get(i + 1).getFinalSemester()), mScores.get(i).getFactor()));
                                i++;
                            } else if (mScores.get(i).getSemester().equals("Học kì 1")) {
                                mScoreFilters.add(new ScoreYear(mScores.get(i).getNameSubject(), "", sumSemester(mScores.get(i).getMouth(), mScores.get(i).getMidSemester(), mScores.get(i).getFinalSemester()), "", mScores.get(i).getFactor()));
                            } else if (mScores.get(i).getSemester().equals("Học kì 2")) {
                                mScoreFilters.add(new ScoreYear(mScores.get(i).getNameSubject(), "", "", sumSemester(mScores.get(i).getMouth(), mScores.get(i).getMidSemester(), mScores.get(i).getFinalSemester()), mScores.get(i).getFactor()));
                            }
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

    private String sumScore(String mouth1, String midSemester1, String finalSemester1, String mouth2, String midSemester2, String finalSemester2) {
        if (mouth1 != null && midSemester1 != null && finalSemester1 != null &&
                mouth2 != null && midSemester2 != null && finalSemester2 != null) {
            Float score1 = (Float.parseFloat(mouth1) + Float.parseFloat(midSemester1) * 2 + Float.parseFloat(finalSemester1) * 3) / 6;
            Float score2 = (Float.parseFloat(mouth2) + Float.parseFloat(midSemester2) * 2 + Float.parseFloat(finalSemester2) * 3) / 6;
            return String.valueOf(new DecimalFormat("##.##").format((score1 + score2) / 2));
        } else {
            return "";
        }
    }


    private String sumSemester(String mouth, String midSemester, String finalSemester) {
        if (mouth != null && midSemester != null && finalSemester != null) {
            Float score = (Float.parseFloat(mouth) + Float.parseFloat(midSemester) * 2 + Float.parseFloat(finalSemester) * 3) / 6;
            return String.valueOf(new DecimalFormat("##.##").format(score));
        } else {
            return "";
        }
    }

    private void initViews(View view) {
        TextView tvSummaryScore = view.findViewById(R.id.tvSummaryYear);

        if (mScoreFilters.size() != 0 && mScoreFilters.size() == 11) {
            for (int i = 0; i < mScoreFilters.size(); i++) {
                if (!mScoreFilters.get(i).getScore().equals("")) {
                    mScore += Float.parseFloat(mScoreFilters.get(i).getScore()) * Float.parseFloat(mScoreFilters.get(i).getFactor());
                }
            }
            mScore = mScore / (mScoreFilters.size() + 2);
            tvSummaryScore.setText(String.valueOf(new DecimalFormat("##.##").format(mScore)));
            getCapacities(mView);
        }
    }

    private void getCapacities(View view) {
        final TextView tvCapacity = view.findViewById(R.id.tvCapacity);
        Api.getApiService().getCapacities().enqueue(new Callback<List<Capacity>>() {
            @Override
            public void onResponse(@NonNull Call<List<Capacity>> call, @NonNull Response<List<Capacity>> response) {
                List<Capacity> capacities = response.body();
                if (capacities != null) {
                    for (int i = 0; i < capacities.size(); i++) {
                        Float above = Float.parseFloat(capacities.get(i).getAbove());
                        Float bellow = Float.parseFloat(capacities.get(i).getBellow());
                        if (bellow <= mScore && mScore <= above) {
                            tvCapacity.setText(String.valueOf(capacities.get(i).getName()));
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Capacity>> call, @NonNull Throwable t) {
            }
        });

    }
}
