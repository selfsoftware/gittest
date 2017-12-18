package com.example.my.godutch.activity.business.businessbase;

import android.content.Context;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessBase {
    private Context mContext ;

    public BusinessBase(Context pContext) {
        mContext = pContext;
    }

    protected String getString(int pResID){
       return mContext.getString(pResID) ;
    }

    protected String getString(int pResID,Object[] pObject){
        return mContext.getString(pResID,pObject) ;
    }
    protected Context getContext(){
        return mContext ;
    }
}
