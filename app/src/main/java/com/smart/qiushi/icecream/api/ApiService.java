package com.smart.qiushi.icecream.api;

import com.smart.qiushi.icecream.model.BannerModel;
import com.smart.qiushi.icecream.model.RefreshModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by shilei on 2018/8/24
 */

public interface ApiService {
    /**
     * 每一个 api 地址都以 / 结尾
     */
    //正式服务器
    String BASE_URL = "http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/";


    @GET("{itemCount}item.json")
    Call<BannerModel> fetchItemsWithItemCount(@Path("itemCount") int itemCount);


    @GET
    Call<List<RefreshModel>> loadContentData(@Url String url);
}
