package com.example.hangtrantd.dacnpm.teacher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.score.Score;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 13/09/2017.
 */

public class ScoreTeacherFragment extends Fragment {
    public static List<Score> mScores;
    public static String mIdStudent;
    public static String mClazz;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        if (getArguments() != null) {
            if (getArguments().getString("pos") != null) {
                mIdStudent = getArguments().getString("pos");
                if (getArguments().getString("clazz") != null) {
                    mClazz = getArguments().getString("clazz");
                    getListScores();
                }
            }
        }
        initViewPager(view);
        return view;
    }

    private void initViewPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.viewPagerScore);
        final TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutScore);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getListScores();
                if (position == 1) {
                    tabPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {

        TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if (position == 0) {
                return new ScoreTeacherSemesterFragment();
            } else {
                return new ScoreTeacherYearFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Học kỳ";
            } else {
                return "Năm";
            }
        }
    }

    public static List<Score> getScores() {
        return mScores;
    }

    public static String getIdStudent() {
        return mIdStudent;
    }

    public static String getClazzStudent() {
        return mClazz;
    }

    private void getListScores() {
        if (mIdStudent != null) {
            Api.getApiService().getScores(mIdStudent).enqueue(new Callback<List<Score>>() {
                @Override
                public void onResponse(@NonNull Call<List<Score>> call, @NonNull Response<List<Score>> response) {
                    if (response.body() != null) {
                        mScores = response.body();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Score>> call, @NonNull Throwable t) {
                }
            });
        }
    }
}
