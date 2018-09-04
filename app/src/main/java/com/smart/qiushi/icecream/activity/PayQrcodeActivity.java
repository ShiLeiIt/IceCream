package com.smart.qiushi.icecream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.qiushi.icecream.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayQrcodeActivity extends AppCompatActivity {

    @Bind(R.id.iv_qrcode)
    ImageView ivQrcode;
    @Bind(R.id.tv_qrcode)
    TextView tvQrcode;
    private boolean mAlipay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_qrcode);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        Intent intent = getIntent();
        mAlipay = intent.getBooleanExtra("alipay", true);
    }

    private void initData() {
        if (mAlipay) {
            Toast.makeText(this, "支付宝页面", Toast.LENGTH_SHORT).show();
            tvQrcode.setText(R.string.open_ali_pay_scan);
        } else {
            Toast.makeText(this, "微信页面", Toast.LENGTH_SHORT).show();
            tvQrcode.setText(R.string.open_wechat_scan);
        }
    }
}
