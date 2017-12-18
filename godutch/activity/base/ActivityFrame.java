package com.example.my.godutch.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.controls.SlideMenuView;
import com.example.my.godutch.activity.controls.SliderMenuItem;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ActivityFrame extends ActivityBase{
    private SlideMenuView slideMenuView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        setContentView(R.layout.activity_main);
    }
    protected void appendMainBody(int pResID){
        LinearLayout _mainBody = (LinearLayout) findViewById(R.id.layMainBody);
        View _view = LayoutInflater.from(this).inflate(pResID,null) ;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT) ;
        _mainBody.addView(_view,params);
    }
    protected void createSlideMenu(int pResID){
        slideMenuView = new SlideMenuView(this) ;
        String[] _menuItemArray = getResources().getStringArray(pResID) ;
        for (int i = 0; i< _menuItemArray.length;i++){
            SliderMenuItem _sliderMenuItem = new SliderMenuItem(i,_menuItemArray[i]) ;
            slideMenuView.add(_sliderMenuItem);
        }
        slideMenuView.bindList();

    }
    protected void slideMenuToggle(){
        slideMenuView.toggle() ;
    }
    protected void createMenu(ContextMenu menu){
        int _Item[] = {1,2} ;
        int groupId = 0 ;
        int order = 0 ;
        for (int i = 0 ; i < _Item.length ; i++){
            switch (_Item[i]){
                case 1:
                    menu.add(groupId,_Item[i],order,R.string.MenuTextEdit) ;
                    break;
                case 2:
                    menu.add(groupId,_Item[i],order,R.string.MenuTextDelete) ;
                    break;
            }
        }
    }

    protected void setTopBarTitle(String pText){
        TextView _TextView = (TextView) findViewById(R.id.tvTopTitle);
        _TextView.setText(pText);
    }
    protected void removeBottomBox() {
        slideMenuView = new SlideMenuView(this) ;
        slideMenuView.removeBottomBox();
    }
}
