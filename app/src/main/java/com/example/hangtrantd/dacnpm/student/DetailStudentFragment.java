package com.example.hangtrantd.dacnpm.student;

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
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.teacher.ShowStudentsFragment;
import com.example.hangtrantd.dacnpm.util.Api;
import com.example.hangtrantd.dacnpm.util.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.hangtrantd.dacnpm.home.MainActivity.mIdUser;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 13/09/2017.
 */
public class DetailStudentFragment extends Fragment implements Spinner.OnItemSelectedListener, RadioButton.OnClickListener {
    private EditText mEdtId;
    private EditText mEdtName;
    private EditText mEdtClass;
    private EditText mEdtBirthDay;
    private EditText mEdtNation;
    private EditText mEdtReligion;
    private EditText mEdtAdress;
    private EditText mEdtPhone;
    private EditText mEdtFatherName;
    private EditText mEdtFatherJob;
    private EditText mEdtMotherName;
    private EditText mEdtMotherJob;
    private List<EditText> mEdts;

    private RadioButton mRadioButtonMale;
    private RadioButton mRadioButtonFemale;
    private Spinner mSpinnerClass;
    private Spinner mSpinnerNation;
    private Spinner mSpinnerReligion;
    private ImageButton mImgBtnDate;

    private Button mBtnEdit;
    private Button mBtnSave;

    private Calendar mCalendar = new GregorianCalendar();
    private Boolean mEnabled = false;
    private Integer countSpinner = 0;
    private Student mStudent = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_student, container, false);
        initViews(view);
        getAPIStudent();
        getInforStudentOfTeacher();
        handleViews();
        return view;
    }

    private void initViews(View view) {
        mEdtId = view.findViewById(R.id.edtIdStudent);
        mEdtName = view.findViewById(R.id.edtNameStudent);
        mEdtClass = view.findViewById(R.id.edtClassStudent);
        mEdtBirthDay = view.findViewById(R.id.edtBirthdayStudent);
        mEdtAdress = view.findViewById(R.id.edtAddressStudent);
        mEdtPhone = view.findViewById(R.id.edtPhoneStudent);
        mEdtNation = view.findViewById(R.id.edtNationStudent);
        mEdtReligion = view.findViewById(R.id.edtReligionStudent);
        mEdtFatherName = view.findViewById(R.id.edtFatherName);
        mEdtFatherJob = view.findViewById(R.id.edtFatherJob);
        mEdtMotherName = view.findViewById(R.id.edtMotherName);
        mEdtMotherJob = view.findViewById(R.id.edtMotherJob);

        mRadioButtonMale = view.findViewById(R.id.radioBtnMale);
        mRadioButtonFemale = view.findViewById(R.id.radioBtnFemale);

        mSpinnerClass = view.findViewById(R.id.spinnerClassStudent);
        mSpinnerClass.setOnItemSelectedListener(this);
        mSpinnerNation = view.findViewById(R.id.spinnerNationStudent);
        mSpinnerNation.setOnItemSelectedListener(this);
        mSpinnerReligion = view.findViewById(R.id.spinnerReligionStudent);
        mSpinnerReligion.setOnItemSelectedListener(this);

        mImgBtnDate = view.findViewById(R.id.imgBtnDate);
        mBtnEdit = view.findViewById(R.id.btnEdit);
        mBtnSave = view.findViewById(R.id.btnSave);
        mEdts = Arrays.asList(mEdtName, mEdtAdress, mEdtPhone, mEdtReligion, mEdtFatherName, mEdtFatherJob, mEdtMotherName, mEdtMotherJob);
    }


    private void getAPIStudent() {
        Api.getApiService().getInforStudent(mIdUser).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                mStudent = response.body();
                if (mStudent != null) {
                    setData(mStudent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
            }
        });
    }

    private void getInforStudentOfTeacher() {
        if (ShowStudentsFragment.getStudent() != null) {
            mStudent = ShowStudentsFragment.getStudent();
            setData(mStudent);
            mBtnEdit.setVisibility(View.GONE);
            mBtnSave.setVisibility(View.GONE);
        }
    }

    private void setData(Student student) {
        mEdtId.setText(student.getId());
        mEdtName.setText(student.getName());
        mEdtBirthDay.setText(student.getBirthDay());
        mEdtAdress.setText(student.getAddress());
        mEdtPhone.setText(student.getPhone());
        mEdtClass.setText(student.getClazz());
        mEdtNation.setText(student.getNation());
        mEdtReligion.setText(student.getReligion());
        mEdtFatherName.setText(student.getFatherName());
        mEdtFatherJob.setText(student.getFatherJob());
        mEdtMotherName.setText(student.getMotherName());
        mEdtMotherJob.setText(student.getMotherJob());

        if (student.getGender().equals("0")) {
            mRadioButtonMale.setChecked(true);
        } else {
            mRadioButtonFemale.setChecked(true);
        }

        Api.getApiService().getClasses().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                List<String> classes = response.body();
                if (classes != null) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, classes);
                    mSpinnerClass.setAdapter(adapter);
                    mSpinnerClass.setSelection(adapter.getPosition(mEdtClass.getText().toString()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call, @NonNull Throwable t) {

            }
        });

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

    private void handleViews() {
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
                            String time = year + "-" + monthOfYear + "-" + dayOfMonth;
                            mEdtBirthDay.setText(time);
                            mBtnSave.setEnabled(true);
                        }
                    };
                    Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioButtonMale.setClickable(true);
                mRadioButtonFemale.setClickable(true);
                mSpinnerNation.setVisibility(View.VISIBLE);
                mSpinnerReligion.setVisibility(View.VISIBLE);
                mSpinnerClass.setVisibility(View.VISIBLE);
                mEdtNation.setVisibility(View.GONE);
                mEdtReligion.setVisibility(View.GONE);
                mEdtClass.setVisibility(View.GONE);
                enableEdt(mEdts, true);
                changeEdt(mEdts);
                mEnabled = true;
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFullName(mEdtName.getText().toString());

                mRadioButtonMale.setClickable(false);
                mRadioButtonFemale.setClickable(false);
                mSpinnerNation.setVisibility(View.GONE);
                mSpinnerReligion.setVisibility(View.GONE);
                mSpinnerClass.setVisibility(View.GONE);
                mEdtNation.setVisibility(View.VISIBLE);
                mEdtReligion.setVisibility(View.VISIBLE);
                mEdtClass.setVisibility(View.VISIBLE);
                enableEdt(mEdts, false);
                mEnabled = false;

                Pattern pattern = Pattern.compile("^[0-9]*$");
                if (mEdtName.getText().toString().equals("") || mEdtAdress.getText().toString().equals("") ||
                        mEdtFatherName.getText().toString().equals("") || mEdtFatherJob.getText().toString().equals("") ||
                        mEdtMotherName.getText().toString().equals("") || mEdtMotherJob.getText().toString().equals("") ||
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

                    Api.getApiService().updateStudent(mEdtId.getText().toString(), mEdtName.getText().toString(),
                            gender, mEdtBirthDay.getText().toString(),
                            mEdtAdress.getText().toString(),
                            mEdtClass.getText().toString(), mEdtNation.getText().toString(),
                            mEdtReligion.getText().toString(), mEdtFatherName.getText().toString(),
                            mEdtFatherJob.getText().toString(), mEdtMotherName.getText().toString(),
                            mEdtMotherJob.getText().toString(), mEdtPhone.getText().toString()).enqueue(new Callback<String>() {
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
    public void onClick(View view) {
        mBtnSave.setEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        countSpinner++;
        if (countSpinner > 2) {
            mBtnSave.setEnabled(true);
            if (adapterView.getId() == R.id.spinnerNationStudent) {
                mEdtNation.setText(mSpinnerNation.getSelectedItem().toString());
            }
            if (adapterView.getId() == R.id.spinnerReligionStudent) {
                mEdtReligion.setText(mSpinnerReligion.getSelectedItem().toString());
            }
            if (adapterView.getId() == R.id.spinnerClassStudent) {
                mEdtClass.setText(mSpinnerClass.getSelectedItem().toString());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
