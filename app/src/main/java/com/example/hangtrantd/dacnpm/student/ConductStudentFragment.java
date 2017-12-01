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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.conduct.Conduct;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Year;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 10/11/2017.
 */

public class ConductStudentFragment extends Fragment implements Spinner.OnItemSelectedListener{
    private View mView;
    private Spinner mSpinnerYear;
    private TextView mTvConduct1;
    private TextView mTvConduct2;
    private Calendar mCalendar = Calendar.getInstance();
    private String mYear;
    private List<Conduct> mConducts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_student_conduct, container, false);
        initViews();
        initSpinners();
        return mView;
    }

    private void initViews(){
        mSpinnerYear = mView.findViewById(R.id.spinnerYear);
        mTvConduct1 = mView.findViewById(R.id.tvConduct1);
        mTvConduct2 = mView.findViewById(R.id.tvConduct2);
    }

    private void initSpinners(){
        mSpinnerYear.setOnItemSelectedListener(this);
        Api.getApiService().getYears().enqueue(new Callback<List<Year>>() {
            @Override
            public void onResponse(@NonNull Call<List<Year>> call, @NonNull Response<List<Year>> response) {
                List<Year> years = response.body();
                List<String> yearNames = new ArrayList<>();
                if (years != null) {
                    for (int i = 0; i <years.size() ; i++) {
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
        if(adapterView.getId()==R.id.spinnerYear){
            mYear = mSpinnerYear.getSelectedItem().toString();
        }
        mTvConduct1.setText("");
        mTvConduct2.setText("");
        getData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getData(){
        Api.getApiService().getConduct(MainActivity.mIdUser).enqueue(new Callback<List<Conduct>>() {
            @Override
            public void onResponse(Call<List<Conduct>> call, Response<List<Conduct>> response) {
                mConducts = response.body();
                if (mConducts != null) {
                    for (int i = 0; i < mConducts.size(); i++) {
                        if (("Năm học "+mConducts.get(i).getYear()).equals(mYear)) {
                            if(mConducts.get(i).getSemester().equals("Học Kì 1")){
                                mTvConduct1.setText(mConducts.get(i).getConduct());
                            }
                            if(mConducts.get(i).getSemester().equals("Học Kì 2")){
                                mTvConduct2.setText(mConducts.get(i).getConduct());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Conduct>> call, Throwable t) {

            }
        });
    }
}
