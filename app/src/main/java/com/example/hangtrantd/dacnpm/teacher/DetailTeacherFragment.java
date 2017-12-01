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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Country;
import com.example.hangtrantd.dacnpm.util.MyAdrress;
import com.example.hangtrantd.dacnpm.util.SubjectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hangtrantd.dacnpm.home.MainActivity.mIdUser;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 13/09/2017.
 */

public class DetailTeacherFragment extends Fragment implements Spinner.OnItemSelectedListener, RadioButton.OnClickListener {
    TextView mEdtId;
    EditText mEdtName;
    EditText mEdtAddress;
    EditText mEdtPhone;
    EditText mEdtBirthDay;
    EditText mEdtSubject;
    RadioButton mRadioButtonMale;
    RadioButton mRadioButtonFemale;
    Spinner mSpinnerProvence;
    Spinner mSpinnerDistrict;
    Spinner mSpinnerSubjectType;
    Button mBtnEdit;
    Button mBtnSave;

    private List<EditText> mEdts;
    private Integer countSpinner = 0;
    private Teacher mTeacher;
    private String mProvence = "";
    private String mDistrict = "";
    private String mSubjectType = "";
    private LinearLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_teacher, container, false);
        ButterKnife.bind(getActivity());
        initViews(view);
        getAPITeacher();
        handleViews();
        return view;
    }

    private void initViews(View view) {
        mEdtId = view.findViewById(R.id.edtIdTeacher);
        mEdtName = view.findViewById(R.id.edtNameTeacher);
        mEdtAddress = view.findViewById(R.id.edtAddressTeacher);
        mEdtPhone = view.findViewById(R.id.edtPhoneTeacher);
        mEdtBirthDay = view.findViewById(R.id.edtBirthdayTeacher);
        mBtnEdit = view.findViewById(R.id.btnEditTeacher);
        mBtnSave = view.findViewById(R.id.btnSaveTeacher);
        mEdtSubject = view.findViewById(R.id.edtSubjectTeacher);
        mContainer = view.findViewById(R.id.llContaint);
        mEdts = Collections.singletonList(mEdtPhone);
        mSpinnerProvence = view.findViewById(R.id.spinnerProvence);
        mSpinnerProvence.setOnItemSelectedListener(this);
        mSpinnerDistrict = view.findViewById(R.id.spinnerDistrict);
        mSpinnerDistrict.setOnItemSelectedListener(this);
        mSpinnerSubjectType = view.findViewById(R.id.spinnerSubjectTeacher);
        mSpinnerSubjectType.setOnItemSelectedListener(this);
        mRadioButtonMale = view.findViewById(R.id.radioBtnMaleTeacher);
        mRadioButtonFemale = view.findViewById(R.id.radioBtnFemaleTeacher);
    }

    private void getAPITeacher() {

        Api.getApiService().getInforTeacher(mIdUser).enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(@NonNull Call<List<Teacher>> call, @NonNull Response<List<Teacher>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    mTeacher = response.body().get(0);
                    setData(mTeacher);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Teacher>> call, @NonNull Throwable t) {
            }
        });

        Api.getApiService().getSubjectTypes().enqueue(new Callback<List<SubjectType>>() {
            @Override
            public void onResponse(@NonNull Call<List<SubjectType>> call, @NonNull Response<List<SubjectType>> response) {
                List<SubjectType> subjectTypes = response.body();
                List<String> subjectNames = new ArrayList<>();
                if (subjectTypes != null) {
                    for (int i = 0; i < subjectTypes.size(); i++) {
                        subjectNames.add(subjectTypes.get(i).getName());
                    }
                    ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectNames);
                    mSpinnerSubjectType.setAdapter(adapterYear);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SubjectType>> call, @NonNull Throwable t) {

            }
        });
    }

    private void setData(Teacher teacher) {
        if (teacher != null) {
            mEdtId.setText(teacher.getId());
            mEdtName.setText(teacher.getName());
            Api.getApiService().getDistrictName(teacher.getAddress()).enqueue(new Callback<MyAdrress>() {
                @Override
                public void onResponse(@NonNull Call<MyAdrress> call, @NonNull Response<MyAdrress> response) {
                    MyAdrress address = response.body();
                    if (address != null) {
                        mDistrict = address.getDistrict();
                        mProvence = address.getProvence();
                        mEdtAddress.setText(mDistrict + ", " + mProvence);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MyAdrress> call, @NonNull Throwable t) {

                }
            });

            mEdtPhone.setText(teacher.getPhone());
            mEdtBirthDay.setText(teacher.getBirthDay());
            mEdtSubject.setText(teacher.getTypeCourse());

            if (teacher.getGender().equals("0")) {
                mRadioButtonMale.setChecked(true);
            } else {
                mRadioButtonFemale.setChecked(true);
            }


            Api.getApiService().getNations().enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(@NonNull Call<List<Country>> call, @NonNull Response<List<Country>> response) {
                    List<Country> countries = response.body();
                    if (countries != null) {
                        List<String> nations = new ArrayList<>();
                        for (int i = 0; i < countries.size(); i++) {
                            nations.add(countries.get(i).getName());
                        }
                        new ArrayAdapter<>(getActivity(), R.layout.item_spinner, nations);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Country>> call, @NonNull Throwable t) {
                }
            });

            Api.getApiService().getReligions().enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(@NonNull Call<List<Country>> call, @NonNull Response<List<Country>> response) {
                    List<Country> countries = response.body();
                    if (countries != null) {
                        List<String> religions = new ArrayList<>();
                        for (int i = 0; i < countries.size(); i++) {
                            religions.add(countries.get(i).getName());
                        }
                         new ArrayAdapter<>(getActivity(), R.layout.item_spinner, religions);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Country>> call, @NonNull Throwable t) {
                }
            });
        }
    }

    private void handleViews() {
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.getApiService().getProvences().enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                        if (response.body()!= null) {
                            List<String> provences = response.body();
                            assert provences != null;
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, provences);
                            mSpinnerProvence.setAdapter(adapter);
                            mSpinnerProvence.setSelection(adapter.getPosition(mProvence));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

                    }
                });

                enableEdt(mEdts, true);
                changeEdt(mEdts);
                mContainer.setVisibility(View.VISIBLE);
                mEdtAddress.setVisibility(View.GONE);
                mEdtSubject.setVisibility(View.GONE);
                mSpinnerSubjectType.setVisibility(View.VISIBLE);
                mSpinnerProvence.setVisibility(View.VISIBLE);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern pattern = Pattern.compile("^[0-9]*$");
                if (mEdtPhone.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Yêu cầu nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!pattern.matcher(mEdtPhone.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    mEdtPhone.setText("");
                } else {
                    ((MainActivity) getActivity()).setFullName(mEdtName.getText().toString());
                    enableEdt(mEdts, false);
                    mContainer.setVisibility(View.GONE);
                    mEdtAddress.setVisibility(View.VISIBLE);
                    mEdtSubject.setVisibility(View.VISIBLE);
                    mBtnSave.setEnabled(false);
                    mSpinnerProvence.setVisibility(View.GONE);
                    mSpinnerDistrict.setVisibility(View.GONE);
                    mSpinnerSubjectType.setVisibility(View.GONE);

                    mBtnSave.setEnabled(false);
                    Api.getApiService().updateTeacher(mEdtId.getText().toString(), mSubjectType, "Đà Nẵng", mEdtPhone.getText().toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            Toast.makeText(getActivity(), "Update success!!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Toast.makeText(getActivity(), "Update false", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        countSpinner++;
        if (countSpinner > 0) {
            mBtnSave.setEnabled(true);

            if (adapterView.getId() == R.id.spinnerProvence) {
                mSpinnerDistrict.setVisibility(View.VISIBLE);
                mProvence = mSpinnerProvence.getSelectedItem().toString();
                Api.getApiService().getDistricts(mProvence).enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                        if (response.body() == null) {
                            return;
                        }
                        List<String> districts = response.body();
                        assert districts != null;
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, districts);
                        mSpinnerDistrict.setAdapter(adapter);
                        mSpinnerDistrict.setSelection(adapter.getPosition(mDistrict));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {
                    }
                });
            }
            if (adapterView.getId() == R.id.spinnerDistrict) {
                mSpinnerDistrict.setVisibility(View.VISIBLE);
                mDistrict = mSpinnerDistrict.getSelectedItem().toString();
                mEdtAddress.setText(mDistrict + ", " + mProvence);
            }

            if (adapterView.getId() == R.id.spinnerSubjectTeacher) {
                mSubjectType = mSpinnerSubjectType.getSelectedItem().toString();
                mEdtSubject.setText(mSubjectType);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        mBtnSave.setEnabled(true);
    }
}
