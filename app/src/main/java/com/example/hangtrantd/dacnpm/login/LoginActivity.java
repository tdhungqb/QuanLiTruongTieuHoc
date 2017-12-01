package com.example.hangtrantd.dacnpm.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.home.MainActivity;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 12/09/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUserName;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
        login();
    }

    private void initView() {
        mEdtUserName = (EditText) findViewById(R.id.edtUserName);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void getUser() {
        Api.getApiService().getInforUser(mEdtUserName.getText().toString())
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        if (response.body() != null&& response.body().size()!=0) {
                            mUser = response.body().get(0);
                        }
                        if (mUser != null) {
                            if (checkValidValue(mUser)) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("User", mUser);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Sai password hoặc username! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                    }
                });
    }

    private boolean checkValidValue(User user) {
        return mEdtUserName.getText().toString().equals(user.getMaso()) && mEdtPassword.getText().toString().equals(user.getPassword());
    }

    private void login() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
