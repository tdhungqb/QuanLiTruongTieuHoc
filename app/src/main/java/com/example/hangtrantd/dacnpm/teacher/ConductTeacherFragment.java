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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.conduct.Conduct;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.NameConduct;
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
 * Created by atHangTran on 05/11/2017.
 */

public class ConductTeacherFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private View mView;
    private Spinner mSpinnerYear;
    private Spinner mSpinnerSemester;
    private Spinner mSpinnerConduct;
    private EditText mEdtConduct;
    private Button mBtnSave;
    private Button mBtnEdit;
    private Calendar mCalendar = Calendar.getInstance();
    private String mSemester;
    private String mYear;
    private String mIdStudent;
    private List<Conduct> mConducts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_teacher_conduct, container, false);
        initViews();
        if (ShowStudentsFragment.getStudent() != null) {
            mIdStudent = ShowStudentsFragment.getStudent().getId();
            mBtnSave.setVisibility(View.VISIBLE);
            mBtnEdit.setVisibility(View.VISIBLE);
        }
        initSpinners();
        handleViews();
        return mView;
    }

    private void initViews() {
        mSpinnerYear = mView.findViewById(R.id.spinnerYear);
        mSpinnerSemester = mView.findViewById(R.id.spinnerSemester);
        mSpinnerConduct = mView.findViewById(R.id.spinnerConduct);
        mEdtConduct = mView.findViewById(R.id.edtConduct);
        mBtnEdit = mView.findViewById(R.id.btnEdit);
        mBtnSave = mView.findViewById(R.id.btnSave);
    }

    private void initSpinners() {
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
                    int currentYear = mCalendar.get(Calendar.YEAR);
                    mSpinnerYear.setSelection(adapterYear.getPosition("Năm học " + currentYear + "-" + (currentYear + 1)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Year>> call, @NonNull Throwable t) {

            }
        });

        mSpinnerSemester.setOnItemSelectedListener(this);
        Api.getApiService().getSemesters().enqueue(new Callback<List<Semester>>() {
            @Override
            public void onResponse(@NonNull Call<List<Semester>> call, @NonNull Response<List<Semester>> response) {
                List<Semester> semesters = response.body();
                List<String> semesterName = new ArrayList<>();
                String currentSemester;
                if (semesters != null) {
                    for (int i = 0; i < semesters.size(); i++) {
                        semesterName.add(semesters.get(i).getTen());
                    }
                    ArrayAdapter<String> adapterSemester = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, semesterName);
                    mSpinnerSemester.setAdapter(adapterSemester);
                    switch (mCalendar.get(Calendar.MONTH)) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            currentSemester = "Học Kì 1";
                            break;
                        default:
                            currentSemester = "Học Kì 2";
                            break;
                    }
                    mSpinnerSemester.setSelection(adapterSemester.getPosition(currentSemester));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Semester>> call, @NonNull Throwable t) {

            }
        });
        Api.getApiService().getConducts().enqueue(new Callback<List<NameConduct>>() {
            @Override
            public void onResponse(@NonNull Call<List<NameConduct>> call, @NonNull Response<List<NameConduct>> response) {
                List<NameConduct> conducts = response.body();
                List<String> conductNames = new ArrayList<>();
                if (conducts != null) {
                    for (int i = 0; i < conducts.size(); i++) {
                        conductNames.add(conducts.get(i).getTen());
                    }
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, conductNames);
                    mSpinnerConduct.setAdapter(adapterYear);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NameConduct>> call, @NonNull Throwable t) {

            }
        });

        mSpinnerConduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mEdtConduct.setText(mSpinnerConduct.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void handleViews() {
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtConduct.setVisibility(View.GONE);
                mSpinnerConduct.setVisibility(View.VISIBLE);
                mBtnSave.setEnabled(true);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnSave.setEnabled(false);
                mEdtConduct.setVisibility(View.VISIBLE);
                mSpinnerConduct.setVisibility(View.GONE);
                Log.d("eeeeeeee", "onClick: "+mYear.substring(8,17));
                Api.getApiService().updateConduct(mIdStudent, mEdtConduct.getText().toString(), mYear.substring(8,17), mSemester).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        Toast.makeText(getActivity(), "Update success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "Update fail!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setData() {
        Api.getApiService().getConduct(mIdStudent).enqueue(new Callback<List<Conduct>>() {
            @Override
            public void onResponse(@NonNull Call<List<Conduct>> call, @NonNull Response<List<Conduct>> response) {
                mConducts = response.body();
                if (mConducts != null) {
                    for (int i = 0; i < mConducts.size(); i++) {
                        if (("Năm học "+mConducts.get(i).getYear()).equals(mYear) && mConducts.get(i).getSemester().equals(mSemester)) {
                            mEdtConduct.setText(mConducts.get(i).getConduct());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Conduct>> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mEdtConduct.setText("");
        mBtnSave.setEnabled(true);
        switch (adapterView.getId()) {
            case R.id.spinnerConduct:
                mEdtConduct.setText(mSpinnerConduct.getSelectedItem().toString());
                break;
            case R.id.spinnerYear:
                mYear = mSpinnerYear.getSelectedItem().toString();
                break;
            case R.id.spinnerSemester:
                mSemester = mSpinnerSemester.getSelectedItem().toString();
                break;
        }
        setData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
