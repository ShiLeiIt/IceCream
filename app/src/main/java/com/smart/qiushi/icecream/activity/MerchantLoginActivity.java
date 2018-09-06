package com.smart.qiushi.icecream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smart.qiushi.icecream.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shilei on 2018/9/5
 * 商户登录页面
 */
public class MerchantLoginActivity extends AppCompatActivity {
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_user_password)
    EditText etUserPassword;
    @Bind(R.id.bt_login)
    Button btLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);
        ButterKnife.bind(this);
        initView();
    }

    //初始化
    private void initView() {

    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etUserName.getText().toString())|| TextUtils.isEmpty(etUserPassword.getText().toString())) {
            if (TextUtils.isEmpty(etUserName.getText().toString())) {
                Toast.makeText(this, "请输入账户名", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (!etUserName.getText().toString().equals("shilei")|| !etUserPassword.getText().toString().equals("123456")) {
                if (!etUserName.getText().toString().equals("shilei")) {
                    Toast.makeText(this, "账户名有误", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "密码有误", Toast.LENGTH_SHORT).show();
                }
            }else{
                startActivity(new Intent(MerchantLoginActivity.this,MerchantCenterManagementActivity.class));
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
