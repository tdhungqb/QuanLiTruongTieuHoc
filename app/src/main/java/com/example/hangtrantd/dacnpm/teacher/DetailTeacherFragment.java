package com.example.hangtrantd.dacnpm.teacher;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
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
    @Bind(R.id.tvIdTeacher)
    TextView mTvId;
    @Bind(R.id.edtNameTeacher)
    EditText mEdtName;
    @Bind(R.id.edtAddressTeacher)
    EditText mEdtAddress;
    @Bind(R.id.edtPhoneTeacher)
    EditText mEdtPhone;
    @Bind(R.id.edtBirthdayTeacher)
    EditText mEdtBirthDay;
    @Bind(R.id.edtNationTeacher)
    EditText mEdtNation;
    @Bind(R.id.edtReligionTeacher)
    EditText mEdtReligion;

    @Bind(R.id.radioBtnMale)
    RadioButton mRadioButtonMale;
    @Bind(R.id.radioBtnFemale)
    RadioButton mRadioButtonFemale;
    @Bind(R.id.spinnerNationTeacher)
    Spinner mSpinnerNation;
    @Bind(R.id.spinnerReligionTeacher)
    Spinner mSpinnerReligion;
    @Bind(R.id.imgBtnDate)
    ImageButton mImgBtnDate;
    @Bind(R.id.btnEdit)
    Button mBtnEdit;
    @Bind(R.id.btnSave)
    Button mBtnSave;

    private List<EditText> mEdts;
    private Calendar mCalendar = new GregorianCalendar();
    private Boolean mEnabled = false;
    private Integer countSpinner = 0;
    private Teacher mTeacher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_teacher, container, false);
        ButterKnife.bind(getActivity());
        initViews();
        getAPITeacher();
        handleViews();
        return view;
    }

    private void initViews() {
        mEdts = Arrays.asList(mEdtName, mEdtAddress, mEdtPhone, mEdtNation, mEdtReligion);
        mSpinnerNation.setOnItemSelectedListener(this);
        mSpinnerReligion.setOnItemSelectedListener(this);
        mRadioButtonMale.setOnClickListener(this);
        mRadioButtonFemale.setOnClickListener(this);
    }

    private void getAPITeacher() {
        Api.getApiService().getInforTeacher(mIdUser).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(@NonNull Call<Teacher> call, @NonNull Response<Teacher> response) {
                mTeacher = response.body();
                if (mTeacher != null) {
                    setData(mTeacher);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Teacher> call, @NonNull Throwable t) {
            }
        });
    }

    private void setData(Teacher teacher) {
        if (teacher != null) {
            mTvId.setText(teacher.getId());
            mEdtName.setText(teacher.getName());
            mEdtAddress.setText(teacher.getAddress());
            mEdtPhone.setText(teacher.getPhone());
            mEdtBirthDay.setText(teacher.getBirthDay());
            mEdtNation.setText(teacher.getNation());
            mEdtReligion.setText(teacher.getReligion());

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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, nations);
                        mSpinnerNation.setAdapter(adapter);
                        mSpinnerNation.setSelection(adapter.getPosition(mEdtNation.getText().toString()));
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, religions);
                        mSpinnerReligion.setAdapter(adapter);
                        mSpinnerReligion.setSelection(adapter.getPosition(mEdtReligion.getText().toString()));
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
                enableEdt(mEdts, true);
                changeEdt(mEdts);
                mEdtNation.setVisibility(View.GONE);
                mEdtReligion.setVisibility(View.GONE);
                mRadioButtonMale.setClickable(true);
                mRadioButtonFemale.setClickable(true);
                mSpinnerNation.setVisibility(View.VISIBLE);
                mSpinnerReligion.setVisibility(View.VISIBLE);
                mEnabled = true;
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFullName(mEdtName.getText().toString());

                enableEdt(mEdts, false);
                mEdtNation.setVisibility(View.VISIBLE);
                mEdtReligion.setVisibility(View.VISIBLE);
                mBtnSave.setEnabled(false);
                mRadioButtonMale.setClickable(false);
                mRadioButtonFemale.setClickable(false);
                mSpinnerNation.setVisibility(View.GONE);
                mSpinnerReligion.setVisibility(View.GONE);
                mEnabled = false;

                Pattern pattern = Pattern.compile("^[0-9]*$");
                if (mEdtName.getText().toString().equals("") || mEdtAddress.getText().toString().equals("") ||
                        mEdtPhone.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Yêu cầu nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else if (!pattern.matcher(mEdtPhone.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    mEdtPhone.setText("");
                } else {
                    mBtnSave.setEnabled(false);
                    String gender;
                    if (mRadioButtonMale.isChecked()) {
                        gender = "0";
                    } else {
                        gender = "1";
                    }
                    Api.getApiService().updateTeacher(mTvId.getText().toString(), mEdtName.getText().toString(), mEdtAddress.getText().toString(), mEdtPhone.getText().toString(), gender, mEdtBirthDay.getText().toString(), mSpinnerReligion.getSelectedItem().toString(), mSpinnerNation.getSelectedItem().toString()).enqueue(new Callback<String>() {
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

        mImgBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEnabled) {
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            mCalendar.set(Calendar.YEAR, year);
                            mCalendar.set(Calendar.MONTH, monthOfYear);
                            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String birthDay = year + "-" + monthOfYear + "-" + dayOfMonth;
                            mEdtBirthDay.setText(birthDay);
                            mBtnSave.setEnabled(true);
                        }
                    };
                    Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
        if (countSpinner > 2) {
            mBtnSave.setEnabled(true);
            if (adapterView.getId() == R.id.spinnerNationTeacher) {
                mEdtNation.setText(mSpinnerNation.getSelectedItem().toString());
            }
            if (adapterView.getId() == R.id.spinnerReligionTeacher) {
                mEdtReligion.setText(mSpinnerReligion.getSelectedItem().toString());
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
