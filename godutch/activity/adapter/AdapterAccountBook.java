package com.example.my.godutch.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.base.AdapterBase;
import com.example.my.godutch.activity.business.BusinessAccountBook;
import com.example.my.godutch.activity.model.AccountBook;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class AdapterAccountBook extends AdapterBase{
    private class Holder{
        ImageView ivAccountBookIcon ;
        TextView tvAccountBookName ;
        TextView tvAccountBookTotal ;
        TextView tvAccountBookMoney ;

    }

    public AdapterAccountBook(Context pContext) {
        super(null, pContext) ;
        BusinessAccountBook _BusinessAccountBook = new BusinessAccountBook(pContext) ;
        List _List = _BusinessAccountBook.getNoHideAccountBook(" And State = 1") ;
        setList(_List);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder _holder ;
        if (convertView == null){

            convertView = getLayoutInflater().inflate(R.layout.account_book_list_item,null) ;
            _holder = new Holder() ;
            _holder.ivAccountBookIcon = (ImageView) convertView.findViewById(R.id.ivAccountBookIcon);
            _holder.tvAccountBookName = (TextView) convertView.findViewById(R.id.tvAccountBookName);
            _holder.tvAccountBookTotal = (TextView) convertView.findViewById(R.id.tvAccountBookTotal);
            _holder.tvAccountBookMoney = (TextView) convertView.findViewById(R.id.tvAccountBookMoney);
            convertView.setTag(_holder);
        }else {
            _holder = (Holder) convertView.getTag();

        }
        AccountBook _AccountBook = (AccountBook)getItem(position);
        if (_AccountBook.getIsDefault() == 1){
            _holder.ivAccountBookIcon.setImageResource(R.drawable.account_book_default_icon);
        }else {
            _holder.ivAccountBookIcon.setImageResource(R.drawable.account_book_normal_icon);
        }

        _holder.tvAccountBookName.setText(_AccountBook.getAccountBookName());
       // _holder.tvAccountBookTotal.setText("");
        // /_holder.tvAccountBookMoney.setText("");
        return convertView;
    }
}
