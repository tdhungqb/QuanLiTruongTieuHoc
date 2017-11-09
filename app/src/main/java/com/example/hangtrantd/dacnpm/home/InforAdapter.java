package com.example.hangtrantd.dacnpm.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 09/10/2017.
 */
public class InforAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Infor> mInfors = new ArrayList<>();
    private OnClickListener mOnClickListener;

    InforAdapter(List<Infor> infors, Context context, OnClickListener onClickListener) {
        mInfors = infors;
        mOnClickListener = onClickListener;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_infor, parent, false);
        return new InforViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InforViewHolder inforViewHolder = (InforViewHolder) holder;
        inforViewHolder.mTvContent.setText(mInfors.get(position).getContent());
        inforViewHolder.mTvTitle.setText(mInfors.get(position).getTitle());
        Picasso.with(mContext).load(mInfors.get(position).getImage()).into(inforViewHolder.mImgDes);
    }

    @Override
    public int getItemCount() {
        return mInfors.size();
    }

    class InforViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvTitle;
        private final TextView mTvContent;
        private final ImageView mImgDes;

        InforViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvContent = itemView.findViewById(R.id.tvContent);
            mImgDes = itemView.findViewById(R.id.imgDes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClickListener.click(getAdapterPosition());
                }
            });
        }
    }

    interface OnClickListener {
        void click(Integer position);
    }
}

