package com.example.my.godutch.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.base.AdapterBase;
import com.example.my.godutch.activity.business.BusinessUser;
import com.example.my.godutch.activity.controls.SliderMenuItem;
import com.example.my.godutch.activity.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class AdapterUser extends AdapterBase{
    private class Holder{
        ImageView ivUserIcon ;
        TextView tvUserName ;

    }

    public AdapterUser(Context pContext) {
        super(null, pContext) ;
        BusinessUser _BusinessUser = new BusinessUser(pContext) ;
        List _List = _BusinessUser.getNoHideUser(" And State = 1") ;
        setList(_List);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder _holder ;
        if (convertView == null){

            convertView = getLayoutInflater().inflate(R.layout.user_list_item,null) ;
            _holder = new Holder() ;
            _holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
            _holder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            convertView.setTag(_holder);
        }else {
            _holder = (Holder) convertView.getTag();

        }
        User _User = (User) getList().get(position);
        _holder.ivUserIcon.setImageResource(R.drawable.usericon);
        _holder.tvUserName.setText(_User.getUserName());
        return convertView;
    }
}
