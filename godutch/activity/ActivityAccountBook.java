package com.example.my.godutch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.AdapterAccountBook;
import com.example.my.godutch.activity.base.ActivityFrame;
import com.example.my.godutch.activity.business.BusinessAccountBook;
import com.example.my.godutch.activity.controls.SlideMenuView;
import com.example.my.godutch.activity.controls.SliderMenuItem;
import com.example.my.godutch.activity.model.AccountBook;
import com.example.my.godutch.activity.utility.RegexTools;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ActivityAccountBook extends ActivityFrame implements SlideMenuView.OnSlideMenuListener {
    private ListView lvAccountBookList;
    private AdapterAccountBook mAdapterAccountBook;
    private BusinessAccountBook mBusinessAccountBook ;
    private AccountBook mSelectAccountBook ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(R.layout.account_book);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuAccountBook);
    }

    public void InitVariable() {

    }

    public void InitView() {
        lvAccountBookList = (ListView) findViewById(R.id.lvAccountBook);
    }

    public void InitListeners() {
        //给需要的控件注册上下文菜单
        registerForContextMenu(lvAccountBookList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //获取到这个上下文菜单的INFO，转化为Adapter类型
        AdapterView.AdapterContextMenuInfo  _AdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //获取控件的适配器
        ListAdapter _ListAdapter = lvAccountBookList.getAdapter() ;
        //利用适配器的getitem方法，获取被点击的项，并且转化为实体类
        mSelectAccountBook = (AccountBook) _ListAdapter.getItem(_AdapterContextMenuInfo.position);
        menu.setHeaderIcon(R.drawable.account_book_icon) ;
        menu.setHeaderTitle(mSelectAccountBook.getAccountBookName()) ;
        createMenu(menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                showAddOrEditDialog(mSelectAccountBook);
                break;
            case 2:
                delete();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void BindData() {
        mAdapterAccountBook = new AdapterAccountBook(this);
        lvAccountBookList.setAdapter(mAdapterAccountBook);
    }

    @Override
    public void onSlideMenuItemClick(View pView, SliderMenuItem pSliderMenuItem) {
        slideMenuToggle();
        if (pSliderMenuItem.getItemID() == 0){
            showAddOrEditDialog(null);
        }
    }

    protected void showAddOrEditDialog(AccountBook pAccountBook){
        View _View = getLayoutInflater().inflate(R.layout.account_book_add_or_edit,null) ;
        EditText _etAccountBookName = (EditText) _View.findViewById(R.id.etAccountBookAddEdit);
        CheckBox _cbCheckBox = (CheckBox) _View.findViewById(R.id.cbIsDefault);
        if (pAccountBook != null){
            _etAccountBookName.setText(pAccountBook.getAccountBookName());
        }

        String _Title ;
        if (pAccountBook == null){
            _Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleTextNew)}) ;
        }else {
            _Title = getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.TitleTextEdit)}) ;
        }

        AlertDialog.Builder _Builder =new AlertDialog.Builder(this) ;
        _Builder.setTitle(_Title)
                .setIcon(R.drawable.account_book_icon)
                .setPositiveButton(R.string.ButtonDialogSave,new onAccountBookAddOrEditListener(pAccountBook,_etAccountBookName,true,_cbCheckBox))
                .setNegativeButton(R.string.ButtonDialogCancel,new onAccountBookAddOrEditListener(null,null,false,null))
                .show() ;
    }

    private class onAccountBookAddOrEditListener implements AlertDialog.OnClickListener{
        private AccountBook mAccountBook ;
        private EditText etAccountBookName ;
        private boolean mIsSaveBottom ;
        private CheckBox checkBoxIsDefault ;
        public onAccountBookAddOrEditListener(AccountBook pAccountBook,EditText petAccountBookName,boolean pIsSaveBottom,CheckBox pCheckBoxIsDefault){
            mAccountBook = pAccountBook ;
            etAccountBookName = petAccountBookName ;
            mIsSaveBottom = pIsSaveBottom ;
            checkBoxIsDefault = pCheckBoxIsDefault ;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
                if (mIsSaveBottom == false){
                    setDialogIsClose(dialog,true) ;
                    return;
                }
                if (mAccountBook == null){
                    mAccountBook = new AccountBook() ;
                }
                String _AccountBookName = etAccountBookName.getText().toString().trim() ;
                Boolean _CheckResult = RegexTools.NAME_REGEXP == _AccountBookName;

                if (!_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataChineseEnglish,new Object[]{etAccountBookName.getHint()}),Toast.LENGTH_SHORT).show();
                    setDialogIsClose(dialog,false) ;
                }else {
                    setDialogIsClose(dialog,true) ;
                }
                _CheckResult = mBusinessAccountBook.isExistAccountBookByAccountBookName(_AccountBookName,mAccountBook.getAccountBookID()) ;
                if (_CheckResult){
                    Toast.makeText(getApplicationContext(),getString(R.string.CheckDataAccountBookExist),Toast.LENGTH_SHORT).show();
                }
                mAccountBook.setAccountBookName(etAccountBookName.getText().toString());
                mAccountBook.setAccountBookName(String.valueOf(etAccountBookName.getText().toString().trim()));
                if (checkBoxIsDefault.isChecked()){
                    mAccountBook.setIsDefault(1);
                }else {
                    mAccountBook.setIsDefault(0);
                }
                if (mAccountBook.getAccountBookID() > 0){
                    mAccountBook.setIsDefault(1);
                }
                boolean _Result = false ;
                if (mAccountBook.getAccountBookID() == 0){
                    _Result = mBusinessAccountBook.insertAccountBook(mAccountBook) ;
                }else {
                    _Result = mBusinessAccountBook.updateAccountBookByAccountBookID(mAccountBook) ;
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
        /*String _Message = getString(R.string.DialogMessageAccountBookDelete,new Object[]{mSelectAccountBook.getAccountBookName()}) ;
        showAlertDialog(R.string.DialogTitleDelete,_Message,new OnDeleteClickListener()) ;*/
    }
    private class OnDeleteClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean _Result = mBusinessAccountBook.hideAccountBookByAccountBookID(mSelectAccountBook.getAccountBookID()) ;
            if (_Result == true){
                BindData();
            }
        }
    }
}


