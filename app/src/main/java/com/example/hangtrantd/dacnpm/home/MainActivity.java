package com.example.hangtrantd.dacnpm.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.login.LoginActivity;
import com.example.hangtrantd.dacnpm.login.User;
import com.example.hangtrantd.dacnpm.student.ConductStudentFragment;
import com.example.hangtrantd.dacnpm.student.DetailStudentFragment;
import com.example.hangtrantd.dacnpm.student.ScoreStudentFragment;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.student.StudentTimeTableFragment;
import com.example.hangtrantd.dacnpm.teacher.DetailTeacherFragment;
import com.example.hangtrantd.dacnpm.teacher.NameStudent;
import com.example.hangtrantd.dacnpm.teacher.ScoreTeacherFragment;
import com.example.hangtrantd.dacnpm.teacher.ShowStudentsFragment;
import com.example.hangtrantd.dacnpm.teacher.Teacher;
import com.example.hangtrantd.dacnpm.teacher.TeacherTimeTableFragment;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ShowStudentsFragment.PositionListener {

    private RelativeLayout mrlContainer;
    private ImageView mImgMenu;
    private ImageView mImgAvatar;
    private DrawerLayout mDrawerLayout;
    private int mPositionSelected = -1;
    private MenuAdapter mAdapter;
    private static FragmentManager mFragmentManager;
    private TextView mTvFullName;
    public static String mIdUser;
    private static Teacher mTeacher;
    private Student mStudent;
    private Boolean exit = false;
    private ProgressBar mProgressBar;
    private Boolean isShowSearch = false;
    public static AutoCompleteTextView mAutoCompleteTextView;
    private SearchAdapter mAdapterSearch;
    private ArrayList<NameStudent> mStudents = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        showBar();
        checkPermission();
        initViews();
        initDrawer();
        showInfor();
    }

    public void showBar() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }

        }, 5000);
    }

    private void checkPermission() {
        Bundle bundle = getIntent().getExtras();
        User user = null;
        if (bundle != null) {
            user = bundle.getParcelable("User");
            if (user != null) {
                mIdUser = user.getMaso();
            }
        }
        if (user != null) {
            switch (user.getPermission()) {
                case 1:
                    break;
                case 2:
                    initViewOfTeacher();
                    break;
                case 3:
                    initViewOfStudent();
                    break;
                case 4:
            }
        }
    }

    private void initViewOfTeacher() {
        final List<ItemMenu> menus = Arrays.asList(new ItemMenu("Thông tin cá nhân"), new ItemMenu("Thời khóa biểu"),
                new ItemMenu("Quản lí học sinh"), new ItemMenu("Tin tức nổi bật"), new ItemMenu("Đăng xuất"));
        getDataMenu(menus, 2);
        getAPITeacher();
    }

    private void initViewOfStudent() {
        final List<ItemMenu> menus = Arrays.asList(new ItemMenu("Thông tin cá nhân"), new ItemMenu("Thời khóa biểu"),
                new ItemMenu("Kết quả học tập"), new ItemMenu("Hành kiểm"),
                new ItemMenu("Tin tức nổi bật"), new ItemMenu("Đăng xuất"));
        getDataMenu(menus, 3);
        getAPIStudent();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mrlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocompleteView);

        mTvFullName = (TextView) findViewById(R.id.tvFullName);
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        mImgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        mFragmentManager = getSupportFragmentManager();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    private void initDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mrlContainer.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };

        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }

        });

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void showInfor() {
        InforFragment inforFragment = new InforFragment();
        FragmentTransaction frInfor = mFragmentManager.beginTransaction();
        frInfor.replace(R.id.frContainer, inforFragment);
        frInfor.commit();
    }

    private void getAPITeacher() {
        Api.getApiService().getInforTeacher(mIdUser).enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(@NonNull Call<List<Teacher>> call, @NonNull Response<List<Teacher>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    mTeacher = response.body().get(0);
                    mTvFullName.setText(mTeacher.getName());
                    if (mTeacher.getGender().equals("0")) {
                        mImgAvatar.setImageResource(R.drawable.ic_male);
                    } else {
                        mImgAvatar.setImageResource(R.drawable.ic_female);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Teacher>> call, @NonNull Throwable t) {
            }
        });
    }

    public static Teacher getTeacher() {
        return mTeacher;
    }

    public void getAPIStudent() {
        Api.getApiService().getInforStudent(mIdUser).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(@NonNull Call<List<Student>> call, @NonNull Response<List<Student>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    mStudent = response.body().get(0);
                    mTvFullName.setText(mStudent.getName());
                    if (mStudent.getGender().equals("0")) {
                        mImgAvatar.setImageResource(R.drawable.ic_male);
                    } else {
                        mImgAvatar.setImageResource(R.drawable.ic_female);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Student>> call, @NonNull Throwable t) {
            }
        });
    }

    public void setFullName(String name) {
        mTvFullName.setText(name);
    }

    private void getDataMenu(final List<ItemMenu> menus, final int i) {
        mAdapter = new MenuAdapter(menus, new MenuAdapter.OnClickListener() {
            @Override
            public void click(Integer position) {
                mDrawerLayout.closeDrawers();
                if (i == 2) {
                    showInforOfTeacher(position);
                } else {
                    showInforOfStudent(position);
                }
                if (mPositionSelected >= 0) {
                    menus.get(mPositionSelected).setCheck(false);
                    mAdapter.notifyItemChanged(mPositionSelected);
                }
                mPositionSelected = position;
                menus.get(position).setCheck(true);
                mAdapter.notifyItemChanged(position);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void showInforOfTeacher(int position) {
        switch (position) {
            case 0:
                getAPITeacher();
                showBar();
                DetailTeacherFragment detailTeacher = new DetailTeacherFragment();
                initFragment(detailTeacher);
                isShowSearch = false;
                invalidateOptionsMenu();
                break;
            case 1:
                getAPITeacher();
                showBar();
                TeacherTimeTableFragment timetableFragment = new TeacherTimeTableFragment();
                initFragment(timetableFragment);
                isShowSearch = false;
                invalidateOptionsMenu();
                break;
            case 2:
                getAPITeacher();
                showBar();
                ShowStudentsFragment showStudentsFragment = new ShowStudentsFragment();
                initFragment(showStudentsFragment);
                isShowSearch = true;
                invalidateOptionsMenu();
                break;
            case 3:
                showBar();
                InforFragment inforFragment = new InforFragment();
                initFragment(inforFragment);
                isShowSearch = false;
                invalidateOptionsMenu();
                break;
            case 4:
                mIdUser = "";
                ShowStudentsFragment.mStudent = null;
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void showInforOfStudent(int position) {
        switch (position) {
            case 0:
                showBar();
                DetailStudentFragment detailStudent = new DetailStudentFragment();
                initFragment(detailStudent);
                break;
            case 1:
                showBar();
                StudentTimeTableFragment timetableFragment = new StudentTimeTableFragment();
                initFragment(timetableFragment);
                break;
            case 2:
                showBar();
                ScoreStudentFragment scoreStudentFragment = new ScoreStudentFragment();
                initFragment(scoreStudentFragment);
                break;
            case 3:
                showBar();
                ConductStudentFragment conductStudentFragment = new ConductStudentFragment();
                initFragment(conductStudentFragment);
                break;
            case 4:
                showBar();
                InforFragment inforFragment = new InforFragment();
                initFragment(inforFragment);
                break;
            case 5:
                mIdUser = "";
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    public static void initFragment(Fragment fragment) {
        FragmentTransaction ftClassManager = mFragmentManager.beginTransaction();
        ftClassManager.replace(R.id.frContainer, fragment);
        ftClassManager.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        if (isShowSearch) {
            search.setVisible(true);
        } else {
            search.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            getNameStudents();
            mAutoCompleteTextView.setVisibility(View.VISIBLE);
            mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mAdapterSearch = new SearchAdapter(MainActivity.this, R.layout.item_search, R.id.tvNameSearch, mStudents);
                    mAutoCompleteTextView.setThreshold(1);
                    mAutoCompleteTextView.setAdapter(mAdapterSearch);
//                    if (editable.length() >= 1) {
//                        mAdapterSearch.getFilter().filter(editable.toString());
//
//                    }
                    mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            mAutoCompleteTextView.setVisibility(View.GONE);
                            mAutoCompleteTextView.setText("");
                            Log.d("xxxxxxxxxxxx1", "onItemClick: " + mAdapterSearch.getIdStudent(i));
                            int pos = ShowStudentsFragment.mAdapter.getPosition(mAdapterSearch.getIdStudent(i));
                            if (pos != -1) {
                                ShowStudentsFragment.mRecyclerView.scrollToPosition(pos);
                                InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                    });
                }
            });
        }
        return true;
    }

    private void getNameStudents() {
        Api.getApiService().getStudents(mIdUser).enqueue(new Callback<List<NameStudent>>() {
            @Override
            public void onResponse(Call<List<NameStudent>> call, Response<List<NameStudent>> response) {
                List<NameStudent> students = response.body();
                if (students != null) {
                    for (int i = 0; i < students.size(); i++) {
                        if (ShowStudentsFragment.getYear() != null) {
                            if (students.get(i).getYear().equals(ShowStudentsFragment.getYear())) {
                                if (ShowStudentsFragment.getClazz() != null) {
                                    if (students.get(i).getClazz().equals(ShowStudentsFragment.getClazz())) {
                                        mStudents.add(students.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NameStudent>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.frContainer);
        if (f instanceof DetailInforFragment) {
            initFragment(new InforFragment());
        } else if (f instanceof InforFragment) {
            if (exit) {
                finish();
                System.exit(0);
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }


    @Override
    public void getPosition(String position, String clazz) {
        Bundle bundle = new Bundle();
        bundle.putString("pos", position);
        bundle.putString("clazz", clazz);
        ScoreTeacherFragment scoreFragment = new ScoreTeacherFragment();
        scoreFragment.setArguments(bundle);
        initFragment(scoreFragment);
    }
}
