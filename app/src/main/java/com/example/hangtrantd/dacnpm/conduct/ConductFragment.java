package com.example.hangtrantd.dacnpm.conduct;

import android.graphics.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.teacher.ShowStudentsFragment;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 05/11/2017.
 */

public class ConductFragment extends Fragment implements Spinner.OnItemSelectedListener {
    private View mView;
    private Spinner mSpinnerYear;
    private Spinner mSpinnerSemester;
    private Spinner mSpinnerConduct;
    private EditText mEdtConduct;
    private EditText mEdtNote;
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
        mView = inflater.inflate(R.layout.fragment_conduct, container, false);
        initViews();
        if (ShowStudentsFragment.getStudent() != null) {
            mIdStudent = ShowStudentsFragment.getStudent().getId();
            mBtnSave.setVisibility(View.VISIBLE);
            mBtnEdit.setVisibility(View.VISIBLE);
        } else {

            mIdStudent = MainActivity.mIdUser;
            mBtnSave.setVisibility(View.GONE);
            mBtnEdit.setVisibility(View.GONE);
            int a = Canvas.ALL_SAVE_FLAG;
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
        mEdtNote = mView.findViewById(R.id.edtNote);
        mBtnEdit = mView.findViewById(R.id.btnEdit);
        mBtnSave = mView.findViewById(R.id.btnSave);
    }

    private void initSpinners() {
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

        mSpinnerSemester.setOnItemSelectedListener(this);
        Api.getApiService().getSemesters().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> semesters = response.body();
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
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

            }
        });

        Api.getApiService().getConducts().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> conducts = response.body();
                if (conducts != null) {
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, conducts);
                    mSpinnerConduct.setAdapter(adapterYear);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

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
                mEdtNote.setEnabled(true);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnSave.setEnabled(false);
                mEdtNote.setEnabled(false);
                mEdtConduct.setVisibility(View.VISIBLE);
                mSpinnerConduct.setVisibility(View.GONE);
                Api.getApiService().updateConduct(mIdStudent, mEdtConduct.getText().toString(), mYear, mSemester, mEdtNote.getText().toString()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getActivity(), "Update success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(), "Update fail!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setData() {
        Api.getApiService().getConduct(mIdStudent).enqueue(new Callback<List<Conduct>>() {
            @Override
            public void onResponse(Call<List<Conduct>> call, Response<List<Conduct>> response) {
                mConducts = response.body();
                if (mConducts != null) {
                    for (int i = 0; i < mConducts.size(); i++) {
                        if (mConducts.get(i).getYear().equals(mYear) && mConducts.get(i).getSemester().equals(mSemester)) {
                            mEdtConduct.setText(mConducts.get(i).getConduct());
                            mEdtNote.setText(mConducts.get(i).getNote());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Conduct>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mEdtConduct.setText("");
        mEdtNote.setText("");
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
