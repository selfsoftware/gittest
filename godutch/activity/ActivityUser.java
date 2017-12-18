package com.example.my.godutch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.AdapterUser;
import com.example.my.godutch.activity.base.ActivityFrame;
import com.example.my.godutch.activity.business.BusinessUser;
import com.example.my.godutch.activity.controls.SlideMenuView;
import com.example.my.godutch.activity.controls.SliderMenuItem;
import com.example.my.godutch.activity.model.User;
import com.example.my.godutch.activity.utility.RegexTools;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ActivityUser extends ActivityFrame implements SlideMenuView.OnSlideMenuListener {
    private ListView lvUserList;
    private AdapterUser mAdapterUser;
    private BusinessUser mBusinessUser ;
    private User mSelectUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(R.layout.user);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuUser);
    }

    public void InitVariable() {

    }

    public void InitView() {
        lvUserList = (ListView) findViewById(R.id.lvUserList);
    }

    public void InitListeners() {
        //给需要的控件注册上下文菜单
        registerForContextMenu(lvUserList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //获取到这个上下文菜单的INFO，转化为Adapter类型
        AdapterView.AdapterContextMenuInfo  _AdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //获取控件的适配器
        ListAdapter _ListAdapter = lvUserList.getAdapter() ;
        //利用适配器的getitem方法，获取被点击的项，并且转化为实体类
        mSelectUser = (User) _ListAdapter.getItem(_AdapterContextMenuInfo.position);
        menu.setHeaderIcon(R.drawable.usericon) ;
        menu.setHeaderTitle(mSelectUser.getUserName()) ;
        createMenu(menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                showAddOrEditDialog(mSelectUser);
                break;
            case 2:
                delete();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void BindData() {
        mAdapterUser = new AdapterUser(this);
        lvUserList.setAdapter(mAdapterUser);
    }

    @Override
    public void onSlideMenuItemClick(View pView, SliderMenuItem pSliderMenuItem) {
        slideMenuToggle();
        if (pSliderMenuItem.getItemID() == 0){
            showAddOrEditDialog(null);
        }
    }

    protected void showAddOrEditDialog(User pUser){
        View _View = getLayoutInflater().inflate(R.layout.user_add_or_edit,null) ;
        EditText _etUserName = (EditText) _View.findViewById(R.id.etUserAddEdit);
        if (pUser != null){
            _etUserName.setText(pUser.getUserName());
        }

        String _Title ;
        if (pUser == null){
            _Title = getString(R.string.DialogTitleUser,new Object[]{getString(R.string.TitleTextNew)}) ;
        }else {
            _Title = getString(R.string.DialogTitleUser,new Object[]{getString(R.string.TitleTextEdit)}) ;
        }

        AlertDialog.Builder _Builder =new AlertDialog.Builder(this) ;
        _Builder.setTitle(_Title)
                .setIcon(R.drawable.usericon)
                .setPositiveButton(R.string.ButtonDialogSave,new onUserAddOrEditListener(pUser,_etUserName,true))
                .setNegativeButton(R.string.ButtonDialogCancel,new onUserAddOrEditListener(null,null,false))
                .show() ;
    }

    private class onUserAddOrEditListener implements AlertDialog.OnClickListener{
        private User mUser ;
        private EditText etUserName ;
        private boolean mIsSaveBottom ;
        public onUserAddOrEditListener(User pUser,EditText petUserName,boolean pIsSaveBottom){
            mUser = pUser ;
            etUserName = petUserName ;
            mIsSaveBottom = pIsSaveBottom ;

        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
                if (mIsSaveBottom == false){
                    setDialogIsClose(dialog,true) ;
                    return;
                }
                if (mUser == null){
                    mUser = new User() ;
                }
                String _UserName = etUserName.getText().toString().trim() ;
                Boolean _CheckResult = RegexTools.NAME_REGEXP == _UserName;

                if (!_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataChineseEnglish,new Object[]{etUserName.getHint()}),Toast.LENGTH_SHORT).show();
                    setDialogIsClose(dialog,false) ;
                }else {
                    setDialogIsClose(dialog,true) ;
                }
                _CheckResult = mBusinessUser.isExistUserByUserName(_UserName,mUser.getUserID()) ;
                if (_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataUserExist),Toast.LENGTH_SHORT).show();
                }
                 mUser.setUserName(etUserName.getText().toString());
                boolean _Result = false ;
                if (mUser.getUserID() == 0){
                    _Result = mBusinessUser.insertUser(mUser) ;
                }else {
                    _Result = mBusinessUser.updateUserByUserID(mUser) ;
                }
                if (_Result){
                    BindData();
                    Toast.makeText(getApplicationContext(),getString(R.string.TipsAddSuccess),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),getString(R.string.TipsAddFail),Toast.LENGTH_SHORT).show();
                }

        }
    }
    private void delete(){
        String _Message = getString(R.string.DialogMessageUserDelete,new Object[]{mSelectUser.getUserName()}) ;
        showAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener()) ;
    }
    private class OnDeleteClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean _Result = mBusinessUser.hideUserByUserID(mSelectUser.getUserID()) ;
            if (_Result == true){
                BindData();
            }
        }
    }
}


