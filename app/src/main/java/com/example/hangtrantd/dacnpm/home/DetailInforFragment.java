package com.example.hangtrantd.dacnpm.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.squareup.picasso.Picasso;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 19/10/2017.
 */

public class DetailInforFragment extends Fragment {
    private TextView mTvTitle;
    private TextView mTvContent;
    private ImageView mImgInfor;
    private Infor mInfor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_infor, container, false);
        initViews(view);
        setData();
        return view;
    }

    private void initViews(View view) {
        mTvTitle = view.findViewById(R.id.tvTilteInfor);
        mTvContent = view.findViewById(R.id.tvContentInfor);
        mImgInfor = view.findViewById(R.id.imgInfor);
    }

    private void setData() {
        mInfor = InforFragment.getInfor();
        if (mInfor != null) {
            mTvTitle.setText(mInfor.getTitle());
            mTvContent.setText(mInfor.getContent());
            Picasso.with(getActivity()).load(mInfor.getImage()).into(mImgInfor);

        }
    }
}
