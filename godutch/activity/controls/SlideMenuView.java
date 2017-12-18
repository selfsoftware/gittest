package com.example.my.godutch.activity.controls;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.AdapterSlideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SlideMenuView {
    private Activity mActivity ;
    private RelativeLayout layBottomBox ;
    private Boolean mIsClosed ;
    private ImageView mMenuButton ;
    private List mMenuList ;
    private OnSlideMenuListener mSlideMenuListener ;
    public SlideMenuView(Activity pActivity) {
        mActivity = pActivity ;
        InitView();

        if (pActivity instanceof OnSlideMenuListener){
            InitListeners();
            mSlideMenuListener = (OnSlideMenuListener) pActivity;
            InitVariable();
        }

    }
    public interface OnSlideMenuListener{
        public void onSlideMenuItemClick(View pView,SliderMenuItem pSliderMenuItem) ;
    }
    public void InitVariable() {
        mMenuList = new ArrayList() ;
        mIsClosed = true ;
    }

    public void InitView() {
        layBottomBox = (RelativeLayout) mActivity.findViewById(R.id.includeBottom);
        mMenuButton = (ImageView) mActivity.findViewById(R.id.iv_box2);
    }

    public void InitListeners() {
        mMenuButton.setOnClickListener(new onSlideMenuClick());
        mMenuButton.setFocusableInTouchMode(true);
        mMenuButton.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() ==KeyEvent.ACTION_UP){
                    toggle();
                }
                return false;
            }
        });
    }

    private void open(){
        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT) ;
        _params.addRule(RelativeLayout.BELOW,R.id.includeTitle);
        layBottomBox.setLayoutParams(_params);
        mIsClosed = false ;
    }

    private void close(){
        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,60) ;
        _params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layBottomBox.setLayoutParams(_params);
        mIsClosed = true ;
    }

    public void toggle(){
        if (mIsClosed == true){
            open();
        }else {
            close();
        }
    }
    public void add(SliderMenuItem pSliderMenuItem){
        mMenuList.add(pSliderMenuItem) ;

    }
    public void bindList(){
        AdapterSlideMenu _adapterSlideMenu = new AdapterSlideMenu(mMenuList,mActivity) ;
        ListView _listView = (ListView) mActivity.findViewById(R.id.lv_slideList);
        _listView.setAdapter(_adapterSlideMenu);
        _listView.setOnItemClickListener(new onSlideMenuItemClick());
    }

    private class onSlideMenuClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            toggle();
        }
    }
    private class onSlideMenuItemClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SliderMenuItem _SliderMenuItem = (SliderMenuItem) parent.getItemAtPosition(position);
            mSlideMenuListener.onSlideMenuItemClick(view,_SliderMenuItem);
        }
    }

    public void removeBottomBox(){
        RelativeLayout _Main = (RelativeLayout) mActivity.findViewById(R.id.layBody);
        _Main.removeView(layBottomBox);
        layBottomBox = null ;
    }
}
