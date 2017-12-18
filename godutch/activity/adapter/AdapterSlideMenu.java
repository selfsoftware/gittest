package com.example.my.godutch.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.base.AdapterBase;
import com.example.my.godutch.activity.controls.SliderMenuItem;

import java.util.List;

/**
 * Created by Administrator on 2017/8/5.
 */

public class AdapterSlideMenu extends AdapterBase{
    private class Holder{
        TextView tvMenuName;
    }
    public AdapterSlideMenu(List pList, Context pContext) {
        super(pList, pContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder _holder ;
        if (convertView == null){

            convertView = getLayoutInflater().inflate(R.layout.bottom_box_listview_item,null) ;
            _holder = new Holder() ;
            _holder.tvMenuName = (TextView) convertView.findViewById(R.id.tvMenuName);
            convertView.setTag(_holder);
        }else {
           _holder = (Holder) convertView.getTag();

        }
        SliderMenuItem _item = (SliderMenuItem) getList().get(position);
        _holder.tvMenuName.setText(_item.getItemName());
        return convertView;
    }
}
