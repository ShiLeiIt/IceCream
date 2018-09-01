package com.smart.qiushi.icecream.model;

import java.util.ArrayList;

/**
 * Created by shilei on 2018/8/24
 */
public class FoodMenu {
    private String menuName;
    private ArrayList<Food> mFoodList;

    public FoodMenu(){
    }

    public FoodMenu(String menuName, ArrayList dishList){
        this.menuName = menuName;
        this.mFoodList = dishList;
    }

    public ArrayList<Food> getFoodList() {
        return mFoodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.mFoodList = foodList;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
