package com.example.my.godutch.activity.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */

public abstract class AdapterBase extends BaseAdapter {

    private List mList ;
    private Context mContext ;
    private LayoutInflater mLayoutInflater ;

    public AdapterBase(List pList, Context pContext) {
        this.mList = pList;
        this.mContext = pContext;
        mLayoutInflater = LayoutInflater.from(mContext) ;
    }
    public Context getContext(){
        return mContext ;
    }
    public void setList(List pList){
        mList = pList ;
    }
    public List getList(){
        return mList ;
    }
    public LayoutInflater getLayoutInflater(){
        return mLayoutInflater ;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
