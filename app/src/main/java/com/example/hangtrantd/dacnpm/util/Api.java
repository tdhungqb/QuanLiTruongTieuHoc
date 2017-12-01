package com.example.hangtrantd.dacnpm.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright © 2017 Asian Tech Co., Ltd.
 * Created by atHangTran on 13/10/2017.
 */

public class Api {
    private static String BASE_URL = "https://webtieuhoc.000webhostapp.com/api/";

    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static APIService getApiService() {
        return getClient().create(APIService.class);
    }
}
