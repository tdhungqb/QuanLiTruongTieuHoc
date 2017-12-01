package com.example.hangtrantd.dacnpm.teacher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 21/10/2017.
 */

public class ShowStudentAdapter extends RecyclerView.Adapter {
    private List<NameStudent> mStudents = new ArrayList<>();
    private OnClickListener mListener;

    ShowStudentAdapter(List<NameStudent> students, OnClickListener listener) {
        mStudents = students;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StudentViewHolder studentViewHolder = (StudentViewHolder) holder;
        studentViewHolder.mTvName.setText(mStudents.get(position).getName());
    }

    public int getPosition(String id) {
        for (int i = 0; i < mStudents.size(); i++) {
            if (mStudents.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final ImageButton mImgDetail;
        private final ImageButton mImgScore;
        private final ImageButton mImgConduct;

        StudentViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tvNameStudent);
            mImgDetail = itemView.findViewById(R.id.imgBtnDetailStudent);
            mImgScore = itemView.findViewById(R.id.imgBtnScore);
            mImgConduct = itemView.findViewById(R.id.imgBtnConduct);

            mImgDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.click(getAdapterPosition(), "detail");
                }
            });

            mImgScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.click(getAdapterPosition(), "score");
                }
            });

            mImgConduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.click(getAdapterPosition(), "conduct");
                }
            });
        }
    }

    interface OnClickListener {
        void click(Integer position, String status);
    }
}
