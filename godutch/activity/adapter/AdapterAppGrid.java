package com.example.my.godutch.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.godutch.R;

/**
 * Created by Administrator on 2017/8/2.
 */

public class AdapterAppGrid extends BaseAdapter {
    private class Holder{
        ImageView ivIcon ;
        TextView tvName ;
    }
    private Integer[] mImageInteger = {
            R.drawable.grid_payout ,
            R.drawable.grid_bill ,
            R.drawable.grid_report ,
            R.drawable.grid_account_book ,
            R.drawable.grid_category ,
            R.drawable.grid_users ,
    } ;
    private String[] mImageString = new String[6] ;
    private Context mContext ;
    public AdapterAppGrid(Context pContext){
        mContext = pContext ;
        mImageString[0] =pContext.getString(R.string.appGridTextPayoutAdd) ;
        mImageString[1] =pContext.getString(R.string.appGridTextPayoutManage) ;
        mImageString[2] =pContext.getString(R.string.appGridTextStaticsManage) ;
        mImageString[3] =pContext.getString(R.string.appGridTextAccountBookManage) ;
        mImageString[4] =pContext.getString(R.string.appGridTextCategoryManage) ;
        mImageString[5] =pContext.getString(R.string.appGridTextUserManage) ;

    }
    @Override
    public int getCount() {
        return mImageString.length;
    }

    @Override
    public Object getItem(int position) {
        return mImageString[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder _holder ;
        if (convertView == null){
            convertView =LayoutInflater.from(mContext).inflate(R.layout.main_body_item,null) ;
            _holder = new Holder() ;
            _holder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon) ;
            _holder.tvName = (TextView) convertView.findViewById(R.id.tvName) ;
            convertView.setTag(_holder);
        }else {
            _holder = (Holder) convertView.getTag();
        }
        _holder.ivIcon.setImageResource(mImageInteger[position]);
        _holder.tvName.setText(mImageString[position]);
        return convertView;
    }
}
