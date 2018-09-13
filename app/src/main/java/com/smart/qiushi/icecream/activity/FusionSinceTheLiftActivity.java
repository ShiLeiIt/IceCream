package com.smart.qiushi.icecream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.smart.qiushi.icecream.R;

/**
 * Created by shilei on 2018/9/7
 * 融合自提页面
 */
public class FusionSinceTheLiftActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fusion_since_the_life);
        initView();
    }

    private void initView() {
     TextView tv =    (TextView) findViewById(R.id.tv_add_fusion_product);
     tv.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent("android.settings.WIFI_SETTINGS");
             startActivity(intent );
         }
     });
    }
}
