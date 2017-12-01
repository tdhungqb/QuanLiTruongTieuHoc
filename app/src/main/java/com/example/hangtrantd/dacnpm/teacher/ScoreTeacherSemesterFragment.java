package com.example.hangtrantd.dacnpm.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Semester;
import com.example.hangtrantd.dacnpm.util.Year;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 24/10/2017.
 */

public class ScoreTeacherSemesterFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private Spinner mSpinnerSubject;
    private Spinner mSpinnerSemester;
    private Spinner mSpinnerYear;
    private EditText mEdtMouth;
    private EditText mEdtMidSemester;
    private EditText mEdtFinalSemester;
    private TextView mTvAverageSemester;
    private Button mBtnSave;
    private Button mBtnEdit;
    private List<String> mSubjects = new ArrayList<>();
    private String mSubject;
    private String mSemester;
    private String mYear;
    private List<EditText> mEdts;
    private Calendar mCalendar = Calendar.getInstance();
    private List<Score> mScores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_score_semester, container, false);
        getScores();
        initSpinners(view);
        initViews(view);
        handleViews();
        return view;
    }

    private void initViews(View view) {
        mEdtMouth = view.findViewById(R.id.edtMouth);
        mEdtMidSemester = view.findViewById(R.id.edtMidSemester);
        mEdtFinalSemester = view.findViewById(R.id.edtFinalSemester);
        mBtnSave = view.findViewById(R.id.btnSave);
        mBtnEdit = view.findViewById(R.id.btnEdit);
        mTvAverageSemester = view.findViewById(R.id.tvAverageSemester);
    }

    private void initSpinners(View view) {
        mSpinnerSubject = view.findViewById(R.id.spinnerSubjectScoreTeacher);
        mSpinnerSubject.setOnItemSelectedListener(this);
        Api.getApiService().getSubjects().enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(@NonNull Call<List<Subject>> call, @NonNull Response<List<Subject>> response) {
                List<Subject> subjects = response.body();
                if (subjects != null) {
                    for (int i = 0; i < subjects.size(); i++) {
                        mSubjects.add(subjects.get(i).getNameSubject());
                    }
                    ArrayAdapter<String> adapterSubject = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mSubjects);
                    mSpinnerSubject.setAdapter(adapterSubject);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Subject>> call, @NonNull Throwable t) {

            }
        });

        mSpinnerSemester = view.findViewById(R.id.spinnerSemesterScoreTeacher);
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
                            mSemester = "Học kì 1";
                            break;
                        default:
                            mSemester = "Học kì 2";
                            break;
                    }
                    mSpinnerSemester.setSelection(adapterSemester.getPosition(mSemester));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Semester>> call, @NonNull Throwable t) {

            }
        });

        mSpinnerYear = view.findViewById(R.id.spinnerYearScoreTeacher);
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
        mEdtMouth.setText("");
        mEdtMidSemester.setText("");
        mEdtFinalSemester.setText("");
        mTvAverageSemester.setText("");
        switch (adapterView.getId()) {
            case R.id.spinnerSubjectScoreTeacher:
                mSubject = mSpinnerSubject.getSelectedItem().toString();
                break;
            case R.id.spinnerSemesterScoreTeacher:
                mSemester = mSpinnerSemester.getSelectedItem().toString();
                break;
            case R.id.spinnerYearScoreTeacher:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
        }
        getScores();
    }

    private void setData(List<Score> scores) {
        String mouth = "";
        String midSemester = "";
        String finalSemester = "";
        for (int j = 0; j < scores.size(); j++) {
            if (scores.get(j).getNameSubject().equals(mSubject)) {
                if (("Năm học "+scores.get(j).getYear()).equals(mYear)) {
                    if (scores.get(j).getSemester().equals(mSemester)) {
                        if (scores.get(j).getMouth() != null) {
                            mEdtMouth.setText(scores.get(j).getMouth());
                            mouth = scores.get(j).getMouth();
                        }
                        if (scores.get(j).getMidSemester() != null) {
                            mEdtMidSemester.setText(scores.get(j).getMidSemester());
                            midSemester = scores.get(j).getMidSemester();
                        }
                        if (scores.get(j).getFinalSemester() != null) {
                            mEdtFinalSemester.setText(scores.get(j).getFinalSemester());
                            finalSemester = scores.get(j).getFinalSemester();
                        }
                        if (!mouth.equals("") && !midSemester.equals("") && !finalSemester.equals("")) {
                            String score = new DecimalFormat("##.##").format(((Float.parseFloat(mouth) + Float.parseFloat(midSemester) * 2 + Float.parseFloat(finalSemester) * 3) / 6));
                            mTvAverageSemester.setText(score);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void handleViews() {
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdts = Arrays.asList(mEdtMouth, mEdtMidSemester, mEdtFinalSemester);
                enableEdt(mEdts, true);
                changeEdt(mEdts);
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new ScoreTeacherSemesterFragment();
                mBtnSave.setEnabled(false);
                enableEdt(mEdts, false);
                Api.getApiService().updateScore(ScoreTeacherFragment.getIdStudent(), mSubject, ScoreTeacherFragment.getClazzStudent(), mSemester, mYear.substring(8,17), mEdtMouth.getText().toString(), mEdtMidSemester.getText().toString(), mEdtFinalSemester.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        Toast.makeText(getActivity(), "Update success!", Toast.LENGTH_SHORT).show();
                        if (!mEdtMouth.getText().toString().equals("") && !mEdtMidSemester.getText().toString().equals("") && !mEdtFinalSemester.getText().toString().equals("")) {
                            String score = new DecimalFormat("##.##").format(((Float.parseFloat(mEdtMouth.getText().toString()) + Float.parseFloat(mEdtMidSemester.getText().toString()) * 2 + Float.parseFloat(mEdtFinalSemester.getText().toString()) * 3) / 6));
                            mTvAverageSemester.setText(score);
                        } else {
                            mTvAverageSemester.setText("");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "Update fail!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void getScores() {
        if (ScoreTeacherFragment.getIdStudent() != null) {
            Api.getApiService().getScores(ScoreTeacherFragment.getIdStudent()).enqueue(new Callback<List<Score>>() {
                @Override
                public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                    if (response.body() != null) {
                        mScores = response.body();
                        setData(mScores);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                }
            });
        }
    }

    private void enableEdt(List<EditText> edts, boolean enabled) {
        for (int i = 0; i < edts.size(); i++) {
            edts.get(i).setEnabled(enabled);
        }
    }

    private void changeEdt(List<EditText> edts) {
        for (int i = 0; i < edts.size(); i++) {
            edts.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mBtnSave.setEnabled(true);
                }
            });
        }
    }
}
