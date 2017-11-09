package com.example.hangtrantd.dacnpm.home;

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
 * Created by atHangTran on 12/09/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter {
    private List<ItemMenu> mMenus = new ArrayList<>();
    private OnClickListener mOnClickListener;

    MenuAdapter(List<ItemMenu> menus, OnClickListener onClickListener) {
        mMenus = menus;
        mOnClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        menuViewHolder.mTvMenu.setText(mMenus.get(position).getName());
        if (mMenus.get(position).isCheck()) {
            menuViewHolder.mTvMenu.setBackgroundResource(R.drawable.press_boder_item_menu);
        } else {
            menuViewHolder.mTvMenu.setBackgroundResource(R.drawable.default_boder_menu_item);
        }
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvMenu;

        MenuViewHolder(View itemView) {
            super(itemView);
            mTvMenu = itemView.findViewById(R.id.tvItemMenu);
            mTvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnClickListener!=null){
                        mOnClickListener.click(getAdapterPosition());
                    }
                }
            });
        }
    }

    interface OnClickListener {
        void click(Integer position);
    }
}
