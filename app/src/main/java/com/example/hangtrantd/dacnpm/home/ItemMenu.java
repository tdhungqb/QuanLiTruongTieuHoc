package com.example.hangtrantd.dacnpm.home;

/**
 * Created by hangtrantd on 13/09/2017.
 */

public class ItemMenu {
    private String name;
    private boolean isCheck;

    public ItemMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
