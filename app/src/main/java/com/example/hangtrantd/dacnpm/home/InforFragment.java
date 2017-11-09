package com.example.hangtrantd.dacnpm.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.util.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 09/10/2017.
 */

public class InforFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private InforAdapter mAdapter;
    private List<Infor> mInfors;
    private static Infor mInfor;
    private InforAdapter.OnClickListener mOnClick = new InforAdapter.OnClickListener() {
        @Override
        public void click(Integer position) {
            mInfor = mInfors.get(position);
            MainActivity.initFragment(new DetailInforFragment());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerInfor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mInfors = new ArrayList<>();
        getInfors();
        return view;
    }

    public static Infor getInfor() {
        return mInfor;
    }

    private void getInfors() {
        Api.getApiService().getInfors().enqueue(new Callback<List<Infor>>() {

            @Override
            public void onResponse(@NonNull Call<List<Infor>> call, @NonNull Response<List<Infor>> response) {
                mInfors = response.body();
                if (mInfors != null) {
                    mAdapter = new InforAdapter(mInfors, getActivity(), mOnClick);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Infor>> call, @NonNull Throwable t) {
            }
        });
    }
}
