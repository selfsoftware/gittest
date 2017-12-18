package com.example.my.godutch.activity.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.my.godutch.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ActivityBase extends Activity{
    //对Activity封装,打开以及切换Activity
    protected void openActivity(Class<?> pCls){
        Intent _intent = new Intent() ;
        _intent.setClass(this,pCls) ;
        startActivity(_intent);
    }
    //Toast提示方法的封装
    protected void showMsg(String pMsg){
        Toast.makeText(this,pMsg,Toast.LENGTH_SHORT).show();
    }
    
    public LayoutInflater getLayoutInflater(){
        LayoutInflater _LayoutInflater = LayoutInflater.from(this) ;
        return _LayoutInflater ;
    }

    public void setDialogIsClose(DialogInterface pDialog,Boolean pIsClose){
        try {
            Field _Field = pDialog.getClass().getSuperclass().getDeclaredField("mShowing") ;
            _Field.setAccessible(true);
            _Field.set(pDialog,pIsClose);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    protected AlertDialog showAlertDialog(int pTitleResID,String pMessage,DialogInterface.OnClickListener pClickListener){
        String _Title = getResources().getString(pTitleResID) ;
        return showAlertDialog(_Title,pMessage,pClickListener) ;
    }

    protected AlertDialog showAlertDialog(String pTitle,String pMessage,DialogInterface.OnClickListener pClickListener){
        return new AlertDialog.Builder(this)
                .setTitle(pTitle)
                .setMessage(pMessage)
                .setPositiveButton(R.string.ButtonTextYes,pClickListener)
                .setNegativeButton(R.string.ButtonTextNo,null)
                .show();
    }
}
