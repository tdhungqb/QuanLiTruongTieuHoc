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
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.student.DetailStudentFragment;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.ClassName;
import com.example.hangtrantd.dacnpm.util.Year;

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
    public static RecyclerView mRecyclerView;
    public static ShowStudentAdapter mAdapter;
    private Spinner mSpinnerClass;
    private Spinner mSpinnerYear;
    private static List<NameStudent> mStudents = new ArrayList<>();
    public  List<NameStudent> mCorrectStudents = new ArrayList<>();
    public static Student mStudent;
    private static List<Score> mScores;
    private static String mClass;
    private static String mYear;
    private Calendar mCalendar = Calendar.getInstance();
    private PositionListener mListener;
    private View mView;

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
        mView = inflater.inflate(R.layout.fragment_students_follow_class, container, false);
        getStudents();
        mRecyclerView = mView.findViewById(R.id.recyclerViewStudents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initSpinners();
        return mView;
    }

    private void initSpinners() {
        mSpinnerClass = mView.findViewById(R.id.spinnerClass);
        mSpinnerClass.setOnItemSelectedListener(this);
        Api.getApiService().getClasses(mIdUser).enqueue(new Callback<List<ClassName>>() {
            @Override
            public void onResponse(@NonNull Call<List<ClassName>> call, @NonNull Response<List<ClassName>> response) {
                List<ClassName> classes = response.body();
                List<String> className= new ArrayList<>();
                if (classes != null) {
                    for (int i = 0; i < classes.size(); i++) {
                        className.add(classes.get(i).getName());
                    }
                    ArrayAdapter<String> adapterClass = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, className);
                    mSpinnerClass.setAdapter(adapterClass);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ClassName>> call, @NonNull Throwable t) {
            }
        });

        mSpinnerYear = mView.findViewById(R.id.spinnerYear);
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
        switch (adapterView.getId()) {
            case R.id.spinnerYear:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
            case R.id.spinnerClass:
                mClass = mSpinnerClass.getSelectedItem().toString();
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
                            if (("Năm học "+mStudents.get(i).getYear()).equals(mYear)) {
                                mCorrectStudents.add(mStudents.get(i));
                            }
                        }
                    }
                    mAdapter = new ShowStudentAdapter(mCorrectStudents, new ShowStudentAdapter.OnClickListener() {
                        @Override
                        public void click(final Integer position, String status) {
                            switch (status) {
                                case "detail":
                                    MainActivity.mAutoCompleteTextView.setVisibility(View.GONE);
                                    Api.getApiService().getInforStudent(mCorrectStudents.get(position).getId()).enqueue(new Callback<List<Student>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                                            if(response.body()!=null&&response.body().size()!=0){
                                                mStudent = response.body().get(0);

                                            }
                                            DetailStudentFragment detailStudent = new DetailStudentFragment();
                                            initFragment(detailStudent);
                                            View menu = getActivity().findViewById(R.id.action_search);
                                            menu.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
                                        }
                                    });
                                    break;
                                case "score":
                                    MainActivity.mAutoCompleteTextView.setVisibility(View.GONE);
                                    Api.getApiService().getScores(mCorrectStudents.get(position).getId()).enqueue(new Callback<List<Score>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                                            mScores = response.body();
                                            if (mScores != null&&mScores.size()!=0) {
                                                mListener.getPosition(mCorrectStudents.get(position).getId(), mScores.get(position).getClazz());
                                                ScoreTeacherFragment scoreTeacherFragment = new ScoreTeacherFragment();
                                                initFragment(scoreTeacherFragment);
                                                View menu = getActivity().findViewById(R.id.action_search);
                                                menu.setVisibility(View.GONE);
                                            }else {
                                                Toast.makeText(getActivity(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                                        }
                                    });
                                    break;
                                case "conduct":
                                    MainActivity.mAutoCompleteTextView.setVisibility(View.GONE);
                                    Api.getApiService().getInforStudent(mCorrectStudents.get(position).getId()).enqueue(new Callback<List<Student>>() {
                                        @Override
                                        public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                                            if(response.body()!=null&&response.body().size()!=0){
                                                mStudent = response.body().get(0);
                                            }
                                            ConductTeacherFragment conductTeacherFragment = new ConductTeacherFragment();
                                            initFragment(conductTeacherFragment);
                                            View menu = getActivity().findViewById(R.id.action_search);
                                            menu.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
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

    public static String getYear() {
        return mYear;
    }

    public static String getClazz() {
        return mClass;
    }

    public interface PositionListener {
        void getPosition(String position, String clazz);
    }

}
