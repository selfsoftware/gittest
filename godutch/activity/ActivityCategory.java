package com.example.my.godutch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.AdapterCategory;
import com.example.my.godutch.activity.base.ActivityFrame;
import com.example.my.godutch.activity.business.BusinessCategory;
import com.example.my.godutch.activity.controls.SlideMenuView;
import com.example.my.godutch.activity.controls.SliderMenuItem;
import com.example.my.godutch.activity.model.Category;
import com.example.my.godutch.activity.utility.RegexTools;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ActivityCategory extends ActivityFrame implements SlideMenuView.OnSlideMenuListener {
    private ExpandableListView lvCategoryList;
    private AdapterCategory mAdapterCategory;
    private BusinessCategory mBusinessCategory ;
    private Category mSelectCategory ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(R.layout.category);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuCategory);
        setTitle();
    }

    public void InitVariable() {

    }

    private void setTitle(){
        int _Count = mBusinessCategory.getNoHideCount() ;
        String _Title = getString(R.string.ActivityTitleCategory,new Object[]{_Count}) ;
        setTopBarTitle(_Title);
    }
    public void InitView() {
        lvCategoryList = (ExpandableListView) findViewById(R.id.lvCategory);
    }

    public void InitListeners() {
        //给需要的控件注册上下文菜单
        registerForContextMenu(lvCategoryList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //获取到这个上下文菜单的INFO，转化为Adapter类型
        ExpandableListView.ExpandableListContextMenuInfo _MenuInfo = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        long _Position = _MenuInfo.packedPosition ;
        int _Type = ExpandableListView.getPackedPositionType(_Position) ;
        int _GroupPosition = ExpandableListView.getPackedPositionGroup(_Position) ;
        switch (_Type){
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                mSelectCategory = (Category) mAdapterCategory.getGroup(_GroupPosition);
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                int _ChildPosition = ExpandableListView.getPackedPositionChild(_Position) ;
                mSelectCategory = (Category) mAdapterCategory.getChild(_GroupPosition,_ChildPosition);
                break;
        }
        //利用适配器的getitem方法，获取被点击的项，并且转化为实体类
        menu.setHeaderIcon(R.drawable.category_icon) ;
        if (mSelectCategory != null){
            menu.setHeaderTitle(mSelectCategory.getCategoryName()) ;
        }

        createMenu(menu);
        menu.add(0,3,3,R.string.ActivityCategoryTotal) ;
        if (mAdapterCategory.getChildCountOfGroup(_GroupPosition) != 0 && mSelectCategory.getParentID() == 0){
            menu.findItem(2).setEnabled(false) ;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent _Intent ;
        switch (item.getItemId()){
            case 1:
                _Intent = new Intent(this,ActivityCategoryAddOrEdit.class) ;
                _Intent.putExtra("Category",mSelectCategory) ;
                startActivityForResult(_Intent,1);
                break;
            case 2:
                delete();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void BindData() {
        mAdapterCategory = new AdapterCategory(this);
        lvCategoryList.setAdapter(mAdapterCategory);
    }

    @Override
    public void onSlideMenuItemClick(View pView, SliderMenuItem pSliderMenuItem) {
        slideMenuToggle();
        if (pSliderMenuItem.getItemID() == 0){
            Intent _Intent = new Intent(this,ActivityCategoryAddOrEdit.class) ;
            startActivityForResult(_Intent,1);
            return;
        }
    }


    /*private class onCategoryAddOrEditListener implements AlertDialog.OnClickListener{
        private Category mCategory ;
        private EditText etCategoryName ;
        private boolean mIsSaveBottom ;
        public onCategoryAddOrEditListener(Category pCategory,EditText petCategoryName,boolean pIsSaveBottom){
            mCategory = pCategory ;
            etCategoryName = petCategoryName ;
            mIsSaveBottom = pIsSaveBottom ;

        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
                if (mIsSaveBottom == false){
                    setDialogIsClose(dialog,true) ;
                    return;
                }
                if (mCategory == null){
                    mCategory = new Category() ;
                }
                String _CategoryName = etCategoryName.getText().toString().trim() ;
                Boolean _CheckResult = RegexTools.NAME_REGEXP == _CategoryName;

                if (!_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataChineseEnglish,new Object[]{etCategoryName.getHint()}),Toast.LENGTH_SHORT).show();
                    setDialogIsClose(dialog,false) ;
                }else {
                    setDialogIsClose(dialog,true) ;
                }
                _CheckResult = mBusinessCategory.isExistCategoryByCategoryName(_CategoryName,mCategory.getCategoryID()) ;
                if (_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataCategoryExist),Toast.LENGTH_SHORT).show();
                }
                 mCategory.setCategoryName(etCategoryName.getText().toString());
                boolean _Result = false ;
                if (mCategory.getCategoryID() == 0){
                    _Result = mBusinessCategory.insertCategory(mCategory) ;
                }else {
                    _Result = mBusinessCategory.updateCategoryByCategoryID(mCategory) ;
                }
                if (_Result){
                    BindData();
                    Toast.makeText(getApplicationContext(),getString(R.string.TipsAddSuccess),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),getString(R.string.TipsAddFail),Toast.LENGTH_SHORT).show();
                }

        }
    }*/
    private void delete(){
        String _Message = getString(R.string.DialogMessageCategoryDelete,new Object[]{mSelectCategory.getCategoryName()}) ;
        showAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener(mSelectCategory)) ;
    }
    private class OnDeleteClickListener implements DialogInterface.OnClickListener{
        private Category mCategory ;
        public OnDeleteClickListener(Category pCategory){
            mCategory = pCategory ;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean _Result = mBusinessCategory.hideCategoryByPath(mSelectCategory.getPath()) ;
            if (_Result == true){
                BindData();
            }else {
                Toast.makeText(getApplicationContext(),R.string.TipsDeleteFail,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BindData();
    }
}


