package com.example.my.godutch.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.AdapterAppGrid;
import com.example.my.godutch.activity.adapter.AdapterCategory;
import com.example.my.godutch.activity.base.ActivityFrame;
import com.example.my.godutch.activity.controls.SlideMenuView;
import com.example.my.godutch.activity.controls.SliderMenuItem;

public class MainActivity extends ActivityFrame implements SlideMenuView.OnSlideMenuListener{
    private GridView gv_AppGrid ;
    private AdapterAppGrid mAdapterAppGrid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(R.layout.main_body);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuActivityMain);
    }
    public void InitVariable(){
        mAdapterAppGrid = new AdapterAppGrid(this) ;
    }
    public void InitView(){
        gv_AppGrid = (GridView) findViewById(R.id.gv_AppGrid) ;
    }
    public void InitListeners(){
        gv_AppGrid.setOnItemClickListener(new onAppGridItemClickListener());
    }
    public void BindData(){
        gv_AppGrid.setAdapter(mAdapterAppGrid);
    }

    @Override
    public void onSlideMenuItemClick(View pView, SliderMenuItem pSliderMenuItem) {
        Toast.makeText(this, pSliderMenuItem.getItemName(), Toast.LENGTH_SHORT).show();
    }
    private class onAppGridItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String _MenuName = (String) parent.getAdapter().getItem(position);
            if (_MenuName.equals(getString(R.string.appGridTextUserManage))){
                openActivity(ActivityUser.class);
                return;
            }
            if (_MenuName.equals(getString(R.string.appGridTextAccountBookManage))){
                openActivity(ActivityAccountBook.class);
                return;
            }
            if (_MenuName.equals(R.string.ActivityTitleCategory)){
                openActivity(AdapterCategory.class);
            }
        }
    }
}
