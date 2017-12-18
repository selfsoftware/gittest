package com.example.my.godutch.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.base.ActivityFrame;
import com.example.my.godutch.activity.business.BusinessCategory;
import com.example.my.godutch.activity.model.Category;
import com.example.my.godutch.activity.utility.RegexTools;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ActivityCategoryAddOrEdit extends ActivityFrame implements View.OnClickListener {
    private BusinessCategory mBusinessCategory ;
    private Button btnSave , btnCancel;
    private EditText etCategoryName ;
    private Spinner spinnerParentID ;
    private Category mCategory ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeBottomBox();
        appendMainBody(R.layout.category_add_or_edit);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuCategory);
        setTitle();

    }

    private void setTitle() {
        String _Title ;
        if (mCategory == null){
            _Title = getString(R.string.ActivityTitleCategoryAddOrEdit,new Object[]{R.string.TitleTextNew}) ;

        }else {
            _Title = getString(R.string.ActivityTitleCategoryAddOrEdit,new Object[]{R.string.TitleTextEdit}) ;
            initData(mCategory) ;
        }
    }

    private void initData(Category pCategory) {
        etCategoryName.setText(pCategory.getCategoryName());
        ArrayAdapter _Adapter = (ArrayAdapter) spinnerParentID.getAdapter();
        if (pCategory.getParentID() != 0){
            int _Position = 0;
            for (int i = 0;i<_Adapter.getCount();i++){
                Category _Category = (Category) _Adapter.getItem(i);
                if (_Category.getParentID() == _Category.getCategoryID()){
                    _Position = _Adapter.getPosition(_Category) ;
                }
            }
            spinnerParentID.setSelection(_Position);
        }else {
            int _Count = mBusinessCategory.getNotHideCountByParentID(pCategory.getCategoryID()) ;
            if (_Count != 0){
                spinnerParentID.setEnabled(false);
            }
        }

    }


    private void BindData() {
        ArrayAdapter<Category> _Adapter = mBusinessCategory.getRootCategoryArrayAdapter() ;
        spinnerParentID.setAdapter(_Adapter);

    }

    private void InitView() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        etCategoryName = (EditText) findViewById(R.id.etCategoryName);
        spinnerParentID = (Spinner) findViewById(R.id.spinnerParentID);

    }

    private void InitVariable() {
        mBusinessCategory = new BusinessCategory(this) ;
        mCategory = (Category) getIntent().getSerializableExtra("Category");

    }
    private void InitListeners() {
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                addOrEditCategory() ;
                break;
            case R.id.btnCancel:
                finish() ;
                break;
        }
    }

    private void addOrEditCategory() {
        String _CategoryName = etCategoryName.getText().toString().trim() ;
        boolean _CheckResult = RegexTools.NAME_REGEXP == _CategoryName ;
        if (!_CheckResult){
            Toast.makeText(getApplicationContext(),R.string.CheckDataChineseEnglish,Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCategory == null){
            mCategory = new Category() ;
            mCategory.setTypeFlag(getString(R.string.PayoutTypeFlag));
            mCategory.setPath("");
        }
        mCategory.setCategoryName(_CategoryName);
        if (!spinnerParentID.getSelectedItem().toString().equals("--请选择--")){
            Category _Category = (Category) spinnerParentID.getSelectedItem();
            if (_Category != null){
                mCategory.setParentID(_Category.getCategoryID());
            }
        }else {
            mCategory.setParentID(0);
        }
        boolean _Result =false ;
        if (mCategory.getCategoryID() == 0){
            _Result = mBusinessCategory.insertCategory(mCategory) ;
        }else {
            _Result = mBusinessCategory.updateCategoryByCategoryID(mCategory) ;
        }
        if (_Result){
            Toast.makeText(getApplicationContext(),getString(R.string.TipsAddSuccess),Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(getApplicationContext(),getString(R.string.TipsAddFail),Toast.LENGTH_SHORT).show();
        }
    }

}
