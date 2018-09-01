package com.smart.qiushi.icecream.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.smart.qiushi.icecream.R;
import com.smart.qiushi.icecream.adpter.PopupFoodAdapter;
import com.smart.qiushi.icecream.imp.ShopCartImp;
import com.smart.qiushi.icecream.model.ShopCart;

import java.util.ArrayList;


public class ShopCartDialog extends Dialog implements View.OnClickListener,ShopCartImp {

    private LinearLayout linearLayout,bottomLayout,clearLayout;
    private FrameLayout shopingcartLayout;
    private ShopCart shopCart;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private RecyclerView recyclerView;
    private PopupFoodAdapter mFoodAdapter;
    private ShopCartDialogImp shopCartDialogImp;
    private TextView mTvPriceTo;
    private RelativeLayout mRlCartDialog;

    public ShopCartDialog(Context context, ShopCart shopCart,int themeResId) {
        super(context,themeResId);
        this.shopCart = shopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        mRlCartDialog = (RelativeLayout) findViewById(R.id.rl_cart_dialog);
        clearLayout = (LinearLayout)findViewById(R.id.clear_layout);
        shopingcartLayout = (FrameLayout)findViewById(R.id.shopping_cart_layout);
        bottomLayout = (LinearLayout)findViewById(R.id.shopping_cart_bottom);
        totalPriceTextView = (TextView)findViewById(R.id.shopping_cart_total_tv);
        totalPriceNumTextView = (TextView)findViewById(R.id.shopping_cart_total_num);
        recyclerView = (RecyclerView)findViewById(R.id.recycleview);
        shopingcartLayout.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        mRlCartDialog.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFoodAdapter = new PopupFoodAdapter(getContext(),shopCart);
        recyclerView.setAdapter(mFoodAdapter);
        mFoodAdapter.setShopCartImp(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }

    private void showTotalPrice(){
        if(shopCart!=null && shopCart.getShoppingTotalPrice()>0){
            totalPriceTextView.setVisibility(View.VISIBLE);
            totalPriceTextView.setText("￥ "+shopCart.getShoppingTotalPrice());
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText(""+shopCart.getShoppingAccount());

        }else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY",1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY",0,1000).setDuration(mDuration)
        );
        animatorSet.start();

        if(shopCartDialogImp!=null){
            shopCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shopping_cart_bottom:
            case R.id.shopping_cart_layout:
                this.dismiss();
                break;
            case R.id.clear_layout:
                clear();
                break;
            case R.id.rl_cart_dialog://点击Dialog空白处
                this.dismiss();
                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if(shopCart.getShoppingAccount()==0){
            this.dismiss();
        }
    }

    public ShopCartDialogImp getShopCartDialogImp() {
        return shopCartDialogImp;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        this.shopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp{
        public void dialogDismiss();
    }

    public void clear(){
        shopCart.clear();
        showTotalPrice();
        if(shopCart.getShoppingAccount()==0){
            this.dismiss();
        }
    }
}
