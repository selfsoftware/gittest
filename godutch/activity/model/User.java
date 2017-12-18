package com.example.my.godutch.activity.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/15.
 */

public class User {
    //用户表主键ID
    private int mUserID ;
    //用户名
    private String mUserName ;
    //创建日期
    private Date mCreateDate = new Date() ;
    //状态 0为禁用 1为启用
    private int state = 1 ;

    public int getUserID() {
        return mUserID;
    }

    public void setUserID(int pUserID) {
        this.mUserID = pUserID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String pUserName) {
        this.mUserName = pUserName;
    }

    public Date getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(Date pCreateDate) {
        this.mCreateDate = pCreateDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int pState) {
        this.state = pState;
    }
}
