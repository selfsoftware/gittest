package com.example.my.godutch.activity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/15.
 */

public class Category implements Serializable{
    //用户表主键ID
    private int mCategoryID ;
    //用户名
    private String mCategoryName ;
    //类型标记名称
    private String mTypeFlag ;
    //父类ID
    private int mParentID = 0 ;
    //路径
    private String mPath ;
    //创建日期
    private Date mCreateDate = new Date() ;
    //状态 0为禁用 1为启用
    private int state = 1 ;

    public int getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(int pCategoryID) {
        this.mCategoryID = pCategoryID;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String pCategoryName) {
        this.mCategoryName = pCategoryName;
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

    public String getTypeFlag() {
        return mTypeFlag;
    }

    public void setTypeFlag(String pTypeFlag) {
        mTypeFlag = pTypeFlag;
    }

    public int getParentID() {
        return mParentID;
    }

    public void setParentID(int pParentID) {
        mParentID = pParentID;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String pPath) {
        mPath = pPath;
    }

    @Override
    public String toString() {
        return mCategoryName;
    }
}
