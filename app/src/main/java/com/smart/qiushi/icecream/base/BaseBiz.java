package com.smart.qiushi.icecream.base;

import com.smart.qiushi.icecream.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shilei on 2018/8/24
 */

public class BaseBiz {

    public ApiService mApiService;

    public BaseBiz() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
