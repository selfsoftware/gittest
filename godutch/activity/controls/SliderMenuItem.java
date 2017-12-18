package com.example.my.godutch.activity.controls;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SliderMenuItem {
    private int mItemID ;
    private String mItemName ;
    public SliderMenuItem(int pItemID,String pItemName){
        mItemID = pItemID ;
        mItemName = pItemName ;
    }
    public int getItemID() {
        return mItemID;
    }

    public void setItemID(int pItemID) {
        this.mItemID = pItemID;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String pItemName) {
        this.mItemName = pItemName;
    }





}
