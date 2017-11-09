package com.example.hangtrantd.dacnpm.student;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.score.Score;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 07/11/2017.
 */

public class ScoreStudentSemesterAdapter extends RecyclerView.Adapter {
    private List<Score> mScores = new ArrayList<>();

    ScoreStudentSemesterAdapter(List<Score> students) {
        mScores = students;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_student_score, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScoreViewHolder scoreViewHolder = (ScoreViewHolder) holder;
        scoreViewHolder.mTvSubject.setText(mScores.get(position).getNameSubject());
        scoreViewHolder.mTvFactor1.setText(mScores.get(position).getMouth());
        scoreViewHolder.mTvFactor2.setText(mScores.get(position).getMidSemester());
        scoreViewHolder.mTvFactor3.setText(mScores.get(position).getFinalSemester());
        scoreViewHolder.mTvAverage.setText(calculateScore(mScores.get(position).getMouth(),
                mScores.get(position).getMidSemester(), mScores.get(position).getFinalSemester()));
    }

    private String calculateScore(String factor1, String factor2, String factor3) {
        if (factor1 != null && factor2 != null && factor3 != null) {
            Float score = (Float.parseFloat(factor1) + Float.parseFloat(factor2) * 2 + Float.parseFloat(factor3) * 3) / 6;
            return String.valueOf(new DecimalFormat("##.##").format(score));
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvSubject;
        private final TextView mTvFactor1;
        private final TextView mTvFactor2;
        private final TextView mTvFactor3;
        private final TextView mTvAverage;

        ScoreViewHolder(View itemView) {
            super(itemView);
            mTvSubject = itemView.findViewById(R.id.tvSubject);
            mTvFactor1 = itemView.findViewById(R.id.tvScoreFactor1);
            mTvFactor2 = itemView.findViewById(R.id.tvScoreFactor2);
            mTvFactor3 = itemView.findViewById(R.id.tvScoreFactor3);
            mTvAverage = itemView.findViewById(R.id.tvScoreAverage);
        }
    }
}
