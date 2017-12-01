package com.example.hangtrantd.dacnpm.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.hangtrantd.dacnpm.R;
import com.example.hangtrantd.dacnpm.teacher.NameStudent;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 12/11/2017.
 */

public class SearchAdapter extends ArrayAdapter<NameStudent> {
    Context context;
    int resource, textViewResourceId;
    List<NameStudent> items, tempItems, suggestions;

    public SearchAdapter(Context context, int resource, int textViewResourceId, List<NameStudent> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<NameStudent>(items); // this makes the difference.
        suggestions = new ArrayList<NameStudent>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_search, parent, false);
        }
        NameStudent people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.tvNameSearch);
            if (lblName != null)
                lblName.setText(people.getName());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    public String getIdStudent(int position){
        return items.get(position).getId();
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((NameStudent) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (NameStudent people : tempItems) {
                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<NameStudent> filterList = (ArrayList<NameStudent>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (NameStudent people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
