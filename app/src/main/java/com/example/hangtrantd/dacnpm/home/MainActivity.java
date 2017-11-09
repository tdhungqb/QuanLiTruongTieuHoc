package com.example.hangtrantd.dacnpm.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.conduct.ConductFragment;
import com.example.hangtrantd.dacnpm.login.LoginActivity;
import com.example.hangtrantd.dacnpm.login.User;
import com.example.hangtrantd.dacnpm.student.DetailStudentFragment;
import com.example.hangtrantd.dacnpm.student.ScoreStudentFragment;
import com.example.hangtrantd.dacnpm.student.Student;
import com.example.hangtrantd.dacnpm.student.StudentTimeTableFragment;
import com.example.hangtrantd.dacnpm.teacher.DetailTeacherFragment;
import com.example.hangtrantd.dacnpm.teacher.ScoreTeacherFragment;
import com.example.hangtrantd.dacnpm.teacher.ShowStudentsFragment;
import com.example.hangtrantd.dacnpm.teacher.Teacher;
import com.example.hangtrantd.dacnpm.teacher.TeacherTimeTableFragment;
import com.example.hangtrantd.dacnpm.util.Api;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Bitmap.createBitmap;

public class MainActivity extends AppCompatActivity implements ShowStudentsFragment.PositionListener {

    private static final int REQUEST_CODE_IMAGE_GALLERY = 1;
    private static final int REQUEST_CODE_IMAGE_CAMERA = 2;
    private static final String KEY_DATA = "data";

    private RelativeLayout mrlContainer;
    private ImageView mImgMenu;
    private DrawerLayout mDrawerLayout;
    private MaterialSearchView mSearchView;
    private int mPositionSelected = -1;
    private MenuAdapter mAdapter;
    private static FragmentManager mFragmentManager;
    private TextView mTvFullName;
    public static String mIdUser;
    private static Teacher mTeacher;
    private Student mStudent;
    private Boolean exit = false;
    private ProgressBar mProgressBar;
    private ImageView mImgAvatar;
    private  Boolean isShowSearch = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        showBar();
        checkPermission();
        initViews();
        initDrawer();
        querySearchView();
        showInfor();
        getAvatar();
    }

    public void showBar() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }

        }, 2000);
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
        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);

        mTvFullName = (TextView) findViewById(R.id.tvFullName);
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        mImgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseImageDialog();
            }
        });

        mFragmentManager = getSupportFragmentManager();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    private void showChooseImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle(R.string.dialog_title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_item);
        arrayAdapter.add(getString(R.string.camera));
        arrayAdapter.add(getString(R.string.gallery));

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if (TextUtils.equals(strName, getString(R.string.camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cropImage(intent);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    cropImage(intent);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE_GALLERY);
                }
            }
        });
        builderSingle.show();
    }

    private void cropImage(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
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

    private void querySearchView() {
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }


    private void showInfor() {
        InforFragment inforFragment = new InforFragment();
        FragmentTransaction frInfor = mFragmentManager.beginTransaction();
        frInfor.replace(R.id.frContainer, inforFragment);
        frInfor.commit();
    }

    private void getAvatar() {
        if (mIdUser != null) {
            Api.getApiService().getAvatar(mIdUser).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    String base64 = response.body();
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    RoundedBitmapDrawable drawable = createRoundBorder(decodedByte);
                    mImgAvatar.setImageDrawable(drawable);
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                }
            });
        }
    }

    private void getAPITeacher() {
        Api.getApiService().getInforTeacher(mIdUser).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(@NonNull Call<Teacher> call, @NonNull Response<Teacher> response) {
                mTeacher = response.body();
                if (mTeacher != null) {
                    mTvFullName.setText(mTeacher.getName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Teacher> call, @NonNull Throwable t) {
            }
        });
    }

    public static Teacher getTeacher() {
        return mTeacher;
    }

    public void getAPIStudent() {
        Api.getApiService().getInforStudent(mIdUser).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {
                mStudent = response.body();
                if (mStudent != null) {
                    mTvFullName.setText(mStudent.getName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
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
                ConductFragment conductFragment = new ConductFragment();
                initFragment(conductFragment);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        if (isShowSearch) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_IMAGE_GALLERY || requestCode == REQUEST_CODE_IMAGE_CAMERA) {
                getImage(data);
                Api.getApiService().updateAvatar(mIdUser, getImageBase64(data)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        Toast.makeText(MainActivity.this, "Update avatar success!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    }
                });
            }
        }
    }

    private void getImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imageBitmap = (Bitmap) extras.get(KEY_DATA);
            RoundedBitmapDrawable drawable = createRoundBorder(imageBitmap);
            mImgAvatar.setImageDrawable(drawable);
        }
    }

    private String getImageBase64(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap imageBitmap = (Bitmap) extras.get(KEY_DATA);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            }
            byte[] byteArrayImage = baos.toByteArray();
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }
        return "";
    }

    private RoundedBitmapDrawable createRoundBorder(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int borderWidth = 1;
        int radius = Math.min(width, height) / 2;
        int squareWidth = Math.min(width, height);
        int newSquare = Math.min(width, height) + borderWidth;

        Bitmap roundedBitmap = createBitmap(newSquare, newSquare, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = borderWidth + squareWidth - width;
        int y = borderWidth + squareWidth - height;
        canvas.drawBitmap(bitmap, x, y, null);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth * 2);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newSquare / 2, paint);
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), roundedBitmap);
        roundedDrawable.setCornerRadius(radius);
        roundedDrawable.setAntiAlias(true);
        return roundedDrawable;
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
