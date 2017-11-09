package com.example.hangtrantd.dacnpm.timetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;

import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 07/11/2017.
 */

public class TimeTableAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mTimetables;

    // 1
    public TimeTableAdapter(Context context, List<String> timetables) {
        mContext = context;
        mTimetables = timetables;
    }

    // 2
    @Override
    public int getCount() {
        return mTimetables.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_timetable, null);
        }
        final TextView textView = (TextView) convertView.findViewById(R.id.tvLesson);
        textView.setText(mTimetables.get(position));
        return convertView;
    }
}