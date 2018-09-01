package com.smart.qiushi.icecream.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.smart.qiushi.icecream.R;
import com.smart.qiushi.icecream.imp.ShopCartImp;
import com.smart.qiushi.icecream.model.Food;
import com.smart.qiushi.icecream.model.FoodMenu;
import com.smart.qiushi.icecream.model.ShopCart;

import java.util.ArrayList;

public class PopupFoodAdapter extends RecyclerView.Adapter {

    private static String TAG = "PopupFoodAdapter";
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<Food> mFoodList;
    private ShopCartImp shopCartImp;

    public PopupFoodAdapter(Context context, ShopCart shopCart) {
        this.shopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        this.mFoodList = new ArrayList<>();
        mFoodList.addAll(shopCart.getShoppingSingleMap().keySet());
        Log.e(TAG, "PopupFoodAdapter: " + this.itemCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_dish, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder) holder;
        final Food food = getDishByPosition(position);
        if (food != null) {
            if (position % 2 == 0) {
                dishholder.right_dish_layout.setBackgroundResource(R.drawable.shape_shopping_cart_item_bg2);
            } else {
                dishholder.right_dish_layout.setBackgroundResource(R.drawable.shape_shopping_cart_item_bg1);
            }

            dishholder.right_dish_name_tv.setText(food.getDishName());
            dishholder.right_dish_price_tv.setText(food.getDishPrice() + "");
            int num = shopCart.getShoppingSingleMap().get(food);
            dishholder.right_dish_account_tv.setText(num + "");


            dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.addShoppingSingle(food)) {
                        notifyItemChanged(position);
                        if (shopCartImp != null)
                            shopCartImp.add(view, position);
                    }
                }
            });

            dishholder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopCart.subShoppingSingle(food)) {
                        mFoodList.clear();
                        mFoodList.addAll(shopCart.getShoppingSingleMap().keySet());
                        itemCount = shopCart.getDishAccount();
                        ;
                        notifyDataSetChanged();
                        if (shopCartImp != null)
                            shopCartImp.remove(view, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public Food getDishByPosition(int position) {
        return mFoodList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class DishViewHolder extends RecyclerView.ViewHolder {
        private TextView right_dish_name_tv;
        private TextView right_dish_price_tv;
        private LinearLayout right_dish_layout;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;
        private TextView right_dish_account_tv;

        public DishViewHolder(View itemView) {
            super(itemView);
            right_dish_name_tv = (TextView) itemView.findViewById(R.id.right_dish_name);
            right_dish_price_tv = (TextView) itemView.findViewById(R.id.right_dish_price);
            right_dish_layout = (LinearLayout) itemView.findViewById(R.id.right_dish_item);
            right_dish_remove_iv = (ImageView) itemView.findViewById(R.id.right_dish_remove);
            right_dish_add_iv = (ImageView) itemView.findViewById(R.id.right_dish_add);
            right_dish_account_tv = (TextView) itemView.findViewById(R.id.right_dish_account);
        }

    }
}
