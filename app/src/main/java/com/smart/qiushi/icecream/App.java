package com.smart.qiushi.icecream;

import android.app.Application;

import com.smart.qiushi.icecream.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static App sInstance;
    private ApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mApiService = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/")
//                .baseUrl("http://127.0.0.1/img.php?img=1")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);

//        Fresco.initialize(this);
    }


    public static App getInstance() {
        return sInstance;
    }

    public ApiService getEngine() {
        return mApiService;
    }
}