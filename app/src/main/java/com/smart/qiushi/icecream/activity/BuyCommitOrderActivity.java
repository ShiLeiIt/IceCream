package com.smart.qiushi.icecream.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.qiushi.icecream.R;
import com.smart.qiushi.icecream.adpter.PopupFoodAdapter;
import com.smart.qiushi.icecream.imp.ShopCartImp;
import com.smart.qiushi.icecream.model.FoodMenu;
import com.smart.qiushi.icecream.model.ShopCart;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shilei on 2018/8/30
 * 订单提交
 */

public class BuyCommitOrderActivity extends AppCompatActivity implements ShopCartImp {
    @Bind(R.id.recycle_buy_order)
    RecyclerView mRecycleBuyOrder;
    @Bind(R.id.iv_check)
    ImageView mIvCheck;
    @Bind(R.id.ll_ali)
    LinearLayout mLlAli;
    @Bind(R.id.iv_uncheck)
    ImageView mIvUncheck;
    @Bind(R.id.ll_wechat)
    LinearLayout mLlWechat;
    @Bind(R.id.tv_commit_total_tv)
    TextView mTvCommitTotalTv;
    @Bind(R.id.tv_original_price)
    TextView mTvOriginalPrice;
    @Bind(R.id.tv_preferential)
    TextView mTvPreferential;//优惠活动Text
    @Bind(R.id.buy_commit_bottom)
    LinearLayout mBuyCommitBottom;
    @Bind(R.id.iv_go_pay)
    ImageView mIvGoPay;

    private ShopCart mShopCart;
    private PopupFoodAdapter mFoodAdapter;
    private ArrayList<FoodMenu> mFoodMenuList;//数据源
    public boolean mAlipay = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_commit_order);
        ButterKnife.bind(this);
        initView();
//        initData();
    }


    private void initView() {
        mShopCart = (ShopCart) getIntent().getSerializableExtra("shopCart");
        mFoodAdapter = new PopupFoodAdapter(BuyCommitOrderActivity.this, mShopCart);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
//                100);//两个400分别为添加图片的大小
//        mIvCheck.setLayoutParams(params);
//        mIvUncheck.setLayoutParams(params);

        mRecycleBuyOrder.setLayoutManager(new LinearLayoutManager(BuyCommitOrderActivity.this));
        mRecycleBuyOrder.setAdapter(mFoodAdapter);
        mFoodAdapter.setShopCartImp(BuyCommitOrderActivity.this);
        mTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//设置中间划线

        if (mShopCart!=null && mShopCart.getShoppingTotalPrice()>0) {
            mTvOriginalPrice.setVisibility(View.VISIBLE);
            mTvOriginalPrice.setText("￥ " + mShopCart.getShoppingTotalPrice());//活动价
            mTvCommitTotalTv.setText(mShopCart.getShoppingTotalPrice() + "");//购物车总价
        }else{
            mTvOriginalPrice.setVisibility(View.GONE);
            mTvCommitTotalTv.setVisibility(View.GONE);
        }

    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BuyCommitOrderActivity.this, BuyActivity.class);
        intent.putExtra("shopcart", mShopCart);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void showTotalPrice() {
        if (mShopCart != null && mShopCart.getShoppingTotalPrice() > 0) {
            mTvOriginalPrice.setVisibility(View.VISIBLE);
            mTvOriginalPrice.setText("￥ " + mShopCart.getShoppingTotalPrice());//活动价
            mTvCommitTotalTv.setText(mShopCart.getShoppingTotalPrice() + "");//购物车总价

        } else {
            mTvOriginalPrice.setVisibility(View.GONE);//活动价
            mTvCommitTotalTv.setVisibility(View.GONE);//总价
        }
    }


    @OnClick({R.id.ll_ali, R.id.ll_wechat, R.id.iv_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ali://选择支付宝支付
                mIvCheck.setImageDrawable(getResources().getDrawable(R.drawable.check));
                mIvUncheck.setImageDrawable(getResources().getDrawable(R.drawable.uncheck));
                mAlipay = true;
                break;
            case R.id.ll_wechat://选择微信支付
                mIvCheck.setImageDrawable(getResources().getDrawable(R.drawable.uncheck));
                mIvUncheck.setImageDrawable(getResources().getDrawable(R.drawable.check));
                mAlipay = false;
                break;
            case R.id.iv_go_pay:
                if (mShopCart != null && mShopCart.getShoppingTotalPrice() > 0) {//通过购物车总价来判断是否跳转

                    if (mAlipay) {    //判断是alipay还是wechatpay
                        Toast.makeText(this, "支付宝支付", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "微信支付", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(BuyCommitOrderActivity.this, PayQrcodeActivity.class);
                    intent.putExtra("alipay", mAlipay);
                    startActivity(intent);
//                    Toast.makeText(this, "提交订单", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "此时购物车为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
