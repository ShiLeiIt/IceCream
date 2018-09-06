package com.smart.qiushi.icecream.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.qiushi.icecream.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shilei on 2018/8/24
 */
public class MainActivity extends Activity {
    @Bind(R.id.tv_buy)
    TextView mTvBuy;
    @Bind(R.id.tv_get)
    TextView mIvGet;
    @Bind(R.id.bt_login)
    Button mBtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_buy, R.id.tv_get, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_buy:
                //购买商品
                startActivity(new Intent(MainActivity.this, BuyActivity.class));
                Toast.makeText(this, "买", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_get:
                Toast.makeText(this, "取", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_login:
                //商户登录
                startActivity(new Intent(MainActivity.this,MerchantLoginActivity.class));
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
