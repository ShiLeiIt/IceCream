package com.smart.qiushi.icecream.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smart.qiushi.icecream.App;
import com.smart.qiushi.icecream.R;
import com.smart.qiushi.icecream.api.ApiService;
import com.smart.qiushi.icecream.model.BannerModel;

import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 开场Banner动画
 */
public class SplashActivity extends Activity implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
    private ApiService mApiService;
    private BGABanner mCubeBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mApiService = App.getInstance().getEngine();
        initView();
    }

    private void initView() {
        mCubeBanner = findViewById(R.id.banner_main_cube);
        loadData(mCubeBanner, 6);
        //设置委托之后,onBannerItemClick()这个方法才走，否则事件触发不了
        mCubeBanner.setDelegate(this);
    }

    /**
     * @param banner
     * @param count  动态获取
     */
    private void loadData(final BGABanner banner, final int count) {
        mApiService.fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();

                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
                banner.setAutoPlayAble(bannerModel.imgs.size() > 1);
                banner.setAdapter(SplashActivity.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);

            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .apply(new RequestOptions().placeholder(R.drawable.holder).error(R.drawable.holder).dontAnimate().centerCrop())
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));

        Toast.makeText(this, "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }
}
