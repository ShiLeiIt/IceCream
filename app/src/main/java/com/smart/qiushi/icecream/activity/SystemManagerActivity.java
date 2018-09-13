package com.smart.qiushi.icecream.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.qiushi.icecream.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shilei on 2018/9/6
 * 系统管理页面
 */
public class SystemManagerActivity extends AppCompatActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_system_manager)
    LinearLayout llSystemManager;
    @Bind(R.id.tv_refriration)
    TextView tvRefriration;
    @Bind(R.id.tv_fresh)
    TextView tvFresh;
    @Bind(R.id.tv_cleaning)
    TextView tvCleaning;
    @Bind(R.id.tv_standby)
    TextView tvStandby;
    @Bind(R.id.tv_thaw)
    TextView tvThaw;
    @Bind(R.id.tv_bus_sterilization)
    TextView tvBusSterilization;
    @Bind(R.id.tv_grow_again)
    TextView tvGrowAgain;
    @Bind(R.id.tv_heavy_operation)
    TextView tvHeavyOperation;
    @Bind(R.id.tv_automatic_discharging)
    TextView tvAutomaticDischarging;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_manager);
        ButterKnife.bind(this);
        initView();
    }

    //初始化
    private void initView() {
        tvTitle.setText(R.string.system_management);
    }

    @OnClick({R.id.iv_back, R.id.tv_refriration, R.id.tv_fresh, R.id.tv_cleaning, R.id.tv_standby, R.id.tv_thaw, R.id.tv_bus_sterilization, R.id.tv_grow_again, R.id.tv_heavy_operation, R.id.tv_automatic_discharging})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_refriration:
                Toast.makeText(this, getString(R.string.refrigeration), Toast.LENGTH_SHORT).show();
                showCenterPopupWindow(view);
                break;
            case R.id.tv_fresh:
                Toast.makeText(this, getString(R.string.fresh), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cleaning:
                Toast.makeText(this, getString(R.string.cleaning), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_standby:
                Toast.makeText(this, getString(R.string.standby), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_thaw:
                Toast.makeText(this, getString(R.string.thaw), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_bus_sterilization:
                Toast.makeText(this, getString(R.string.bus_sterilization), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_grow_again:
                Toast.makeText(this, getString(R.string.grow_again), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_heavy_operation:
                Toast.makeText(this, getString(R.string.heavy_operation), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_automatic_discharging:
                Toast.makeText(this, getString(R.string.automatic_discharging), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //制冷
    public void showCenterPopupWindow(View view) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow_system_manager_item, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btCancel = (Button) contentView.findViewById(R.id.bt_cancel);
        Button btNext = (Button) contentView.findViewById(R.id.bt_next);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SystemManagerActivity.this, getString(R.string.refrigeration), Toast.LENGTH_SHORT).show();
            }
        });
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        mPopupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        // 设置PopupWindow显示在中间
        mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPopupWindow!=null) {
            mPopupWindow.dismiss();
        }
        finish();

    }
}
