package com.example.hangtrantd.dacnpm.score;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 01/11/2017.
 */

public class ScoreYearAdapter extends RecyclerView.Adapter {
    private List<ScoreYear> mScores = new ArrayList<>();

    public ScoreYearAdapter(List<ScoreYear> students) {
        mScores = students;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_subject_year, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;
        scoreViewHolder.mTvSubject.setText(mScores.get(position).getSubject());
        scoreViewHolder.mtvScore.setText(mScores.get(position).getScore());
        scoreViewHolder.mTvSemester1.setText(mScores.get(position).getSemester1());
        scoreViewHolder.mTvSemester2.setText(mScores.get(position).getSemester2());
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvSubject;
        private final TextView mTvSemester1;
        private final TextView mTvSemester2;
        private final TextView mtvScore;

        ScoreViewHolder(View itemView) {
            super(itemView);
            mTvSubject = itemView.findViewById(R.id.tvSubject);
            mtvScore = itemView.findViewById(R.id.tvScore);
            mTvSemester1 = itemView.findViewById(R.id.tvSemester1Score);
            mTvSemester2 = itemView.findViewById(R.id.tvSemester2Score);
        }
    }
}
