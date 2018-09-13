package com.smart.qiushi.icecream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smart.qiushi.icecream.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shilei on 2018/9/5
 * 商户中心管理页面
 */
public class MerchantCenterManagementActivity extends AppCompatActivity {
    @Bind(R.id.tv_data_center)
    TextView tvDataCenter;
    @Bind(R.id.tv_my_sales)
    TextView tvMySales;
    @Bind(R.id.tv_system_manager)
    TextView tvSystemManager;
    @Bind(R.id.tv_list_of_goods)
    TextView tvListOfGoods;
    @Bind(R.id.tv_go_to_replenishment)
    TextView tvGoToReplenishment;
    @Bind(R.id.tv_convergence_to_the)
    TextView tvConvergenceToThe;
    @Bind(R.id.bt_log_out)
    Button btLogOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_center_manager);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_data_center, R.id.tv_my_sales, R.id.tv_system_manager, R.id.tv_list_of_goods, R.id.tv_go_to_replenishment, R.id.tv_convergence_to_the, R.id.bt_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data_center://数据中心
                tvDataCenter.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_left_bg));
                tvMySales.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvSystemManager.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvListOfGoods.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvGoToReplenishment.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvConvergenceToThe.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                break;
            case R.id.tv_my_sales://我的销量
                tvMySales.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_right_bg));
                tvDataCenter.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvSystemManager.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvListOfGoods.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvGoToReplenishment.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvConvergenceToThe.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                break;
            case R.id.tv_system_manager://系统管理
                tvSystemManager.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_left_bg));
                tvDataCenter.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvMySales.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvListOfGoods.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvGoToReplenishment.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvConvergenceToThe.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                startActivity(new Intent(MerchantCenterManagementActivity.this,SystemManagerActivity.class));
                break;
            case R.id.tv_list_of_goods://商品列表
                tvListOfGoods.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_right_bg));
                tvDataCenter.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvMySales.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvSystemManager.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvGoToReplenishment.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvConvergenceToThe.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                break;
            case R.id.tv_go_to_replenishment://去补货
                tvGoToReplenishment.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_left_bg));
                tvDataCenter.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvMySales.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvSystemManager.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvListOfGoods.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvConvergenceToThe.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                break;
            case R.id.tv_convergence_to_the://融合自提
                tvConvergenceToThe.setBackground(getResources().getDrawable(R.drawable.shape_merchant_tv_right_bg));
                tvDataCenter.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvMySales.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvSystemManager.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvListOfGoods.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                tvGoToReplenishment.setBackgroundColor(getResources().getColor(R.color.merchant_tv_normal_bg));
                startActivity(new Intent(MerchantCenterManagementActivity.this,FusionSinceTheLiftActivity.class));
                break;
            case R.id.bt_log_out://退出登录
                break;
        }
    }
}
