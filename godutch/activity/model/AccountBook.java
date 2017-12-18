package com.example.my.godutch.activity.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/26.
 */

public class AccountBook {
    //账本表的主键ID
    private int mAccountBookID ;
    //账本名称
    private String mAccountBookName ;
    //添加日期
    private Date mCreateDate = new Date() ;
    //状态：0失效，1启用
    private int mState = 1 ;
    //是否默认账本
    private int mIsDefault ;

    public int getAccountBookID() {
        return mAccountBookID;
    }

    public void setAccountBookID(int pAccountBookID) {
        mAccountBookID = pAccountBookID;
    }

    public String getAccountBookName() {
        return mAccountBookName;
    }

    public void setAccountBookName(String pAccountBookName) {
        mAccountBookName = pAccountBookName;
    }

    public Date getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Date pCreateDate) {
        mCreateDate = pCreateDate;
    }

    public int getState() {
        return mState;
    }

    public void setState(int pState) {
        mState = pState;
    }

    public int getIsDefault() {
        return mIsDefault;
    }

    public void setIsDefault(int pIsDefault) {
        mIsDefault = pIsDefault;
    }
}
