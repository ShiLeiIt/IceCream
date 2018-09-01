package com.smart.qiushi.icecream.model;

import java.io.Serializable;


public class Food implements Serializable {

    private String dishName;//名字
    private double dishPrice;//单价
    private int dishAmount;//总量
    private int dishRemain;

    public Food(String dishName, double dishPrice, int dishAmount) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishAmount = dishAmount;
        this.dishRemain = dishAmount;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(int dishAmount) {
        this.dishAmount = dishAmount;
    }

    public int getDishRemain() {
        return dishRemain;
    }

    public void setDishRemain(int dishRemain) {
        this.dishRemain = dishRemain;
    }

    public int hashCode() {
        int code = this.dishName.hashCode() + (int) this.dishPrice;
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        return obj instanceof Food &&
                this.dishName.equals(((Food) obj).dishName) &&
                this.dishPrice == ((Food) obj).dishPrice &&
                this.dishAmount == ((Food) obj).dishAmount &&
                this.dishRemain == ((Food) obj).dishRemain;
    }
}
