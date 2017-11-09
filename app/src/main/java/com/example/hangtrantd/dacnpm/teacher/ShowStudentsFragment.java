package com.example.hangtrantd.dacnpm.teacher;

import android.content.Context;
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
import com.example.hangtrantd.dacnpm.conduct.ConductFragment;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.student.DetailStudentFragment;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hangtrantd.dacnpm.home.MainActivity.initFragment;
import static com.example.hangtrantd.dacnpm.home.MainActivity.mIdUser;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 21/10/2017.
 */

public class ShowStudentsFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private RecyclerView mRecyclerView;
    private ShowStudentAdapter mAdapter;
    private Spinner mSpinnerClass;
    private Spinner mSpinnerYear;
    private List<NameStudent> mStudents = new ArrayList<>();
    private List<NameStudent> mCorrectStudents = new ArrayList<>();
    public static Student mStudent;
    private static List<Score> mScores;
    private String mClass;
    private String mYear;
    private Calendar mCalendar = Calendar.getInstance();
    private PositionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PositionListener) {
            mListener = (PositionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students_follow_class, container, false);
        getStudents();
        mRecyclerView = view.findViewById(R.id.recyclerViewStudents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initSpinners(view);
        return view;
    }

    private void initSpinners(View view) {
        mSpinnerClass = view.findViewById(R.id.spinnerClass);
        mSpinnerClass.setOnItemSelectedListener(this);
        Api.getApiService().getClasses(mIdUser).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> classes = response.body();
                if (classes != null) {
                    ArrayAdapter<String> adapterClass = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classes);
                    mSpinnerClass.setAdapter(adapterClass);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
            }
        });

        mSpinnerYear = view.findViewById(R.id.spinnerYear);
        mSpinnerYear.setOnItemSelectedListener(this);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerYear:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
            case R.id.spinnerClass:
                mClass = mSpinnerClass.getSelectedItem().toString().substring(4, 6);
                break;
        }
        getStudents();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getStudents() {
        Api.getApiService().getStudents(mIdUser).enqueue(new Callback<List<NameStudent>>() {
            @Override
            public void onResponse(@NonNull Call<List<NameStudent>> call, @NonNull Response<List<NameStudent>> response) {
                mStudents = response.body();
                if (mStudents != null) {
                    mCorrectStudents.clear();
                    for (int i = 0; i < mStudents.size(); i++) {
                        if (mStudents.get(i).getClazz().equals(mClass)) {
                            if (mStudents.get(i).getYear().equals(mYear)) {
                                mCorrectStudents.add(mStudents.get(i));
                            }
                        }
                    }
                    mAdapter = new ShowStudentAdapter(mCorrectStudents, new ShowStudentAdapter.OnClickListener() {
                        @Override
                        public void click(final Integer position, String status) {
                            switch (status) {
                                case "detail":
                                    Api.getApiService().getInforStudent(mCorrectStudents.get(position).getId()).enqueue(new Callback<Student>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                                            mStudent = response.body();
                                            DetailStudentFragment detailStudent = new DetailStudentFragment();
                                            initFragment(detailStudent);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                                        }
                                    });
                                    break;
                                case "score":
                                    Api.getApiService().getScores(mCorrectStudents.get(position).getId()).enqueue(new Callback<List<Score>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                                            mScores = response.body();
                                            if (mScores != null) {
                                                mListener.getPosition(mCorrectStudents.get(position).getId(), mScores.get(position).getClazz());
                                                ScoreTeacherFragment scoreTeacherFragment = new ScoreTeacherFragment();
                                                initFragment(scoreTeacherFragment);
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                                        }
                                    });
                                    break;
                                case "conduct":
                                    Api.getApiService().getInforStudent(mCorrectStudents.get(position).getId()).enqueue(new Callback<Student>() {
                                        @Override
                                        public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                                            mStudent = response.body();
                                            ConductFragment conductFragment = new ConductFragment();
                                            initFragment(conductFragment);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                                        }
                                    });
                                    break;
                            }
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NameStudent>> call, @NonNull Throwable t) {
            }
        });
    }

    public static Student getStudent() {
        return mStudent;
    }

    public static List<Score> getScores() {
        return mScores;
    }

    public interface PositionListener {
        void getPosition(String position, String clazz);
    }
}
