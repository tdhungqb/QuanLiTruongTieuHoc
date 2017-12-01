package com.example.hangtrantd.dacnpm.util;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 30/11/2017.
 */

public class Authorization {
    private String userId;
    private String accessToken;

    public Authorization(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
