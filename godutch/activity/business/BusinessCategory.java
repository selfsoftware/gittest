package com.example.my.godutch.activity.business;

import android.content.ContentValues;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.business.businessbase.BusinessBase;
import com.example.my.godutch.activity.database.sqlitedal.SQLiteDALCategory;
import com.example.my.godutch.activity.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessCategory extends BusinessBase {
    private SQLiteDALCategory mSQLiteDALCategory ;
    private final String TYPE_FLAG = " And TypeFlag = " + getString(R.string.PayoutTypeFlag) ;
    public BusinessCategory(Context pContext) {
        super(pContext);
        mSQLiteDALCategory = new SQLiteDALCategory(pContext) ;
    }
    //商业
    public boolean insertCategory(Category pCategory){
        mSQLiteDALCategory.beginTransaction();
        try {
            boolean _Result = mSQLiteDALCategory.insertCategory(pCategory);
            boolean _Result2 = true;
            Category _ParentCategory = (Category) getCategoryByCategoryID(pCategory.getParentID());
            String _Path ;
            if (_ParentCategory != null){
                _Path = _ParentCategory.getPath() + pCategory.getCategoryID() + "." ;
            }else {
                _Path = pCategory.getCategoryID() + "." ;
            }
            pCategory.setPath(_Path);
            _Result2 = updateCategoryInsertTypeByCategoryID(pCategory) ;

            if (_Result && _Result2) {
                mSQLiteDALCategory.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false ;
        }
        finally {
            mSQLiteDALCategory.endTransaction();
        }


    }

    private boolean setIsDefault(int pCategoryID) {
        String _Condition = " IsDefault = 1 "  ;
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("IsDefault",0);
        boolean _Result = mSQLiteDALCategory.updateCategory(_Condition,_ContentValues) ;
        _Condition = "CategoryID = " + pCategoryID ;
        _ContentValues.clear();
        _ContentValues.put("IsDefault",1);
        boolean _Result2 = mSQLiteDALCategory.updateCategory(_Condition,_ContentValues) ;
        if (_Result && _Result2){
            return true ;
        }else {
            return false ;
        }

    }

    public boolean deleteCategoryByCategoryID(Category pCategory){
        String _Condition = " And CategoryID = " + pCategory.getCategoryID();
        boolean _Result = mSQLiteDALCategory.deleteCategory(_Condition) ;
        return _Result ;
    }
    public boolean updateCategoryInsertTypeByCategoryID(Category pCategory){
        String _Condition = " CategoryID = " + pCategory.getCategoryID();
        Boolean _Result = mSQLiteDALCategory.updateCategory(pCategory,_Condition) ;
        if (_Result){
            return true ;
        }else {
            return false ;
        }
    }
    public boolean updateCategoryByCategoryID(Category pCategory){

        mSQLiteDALCategory.beginTransaction();
        try {
            String _Condition = " CategoryID = " + pCategory.getCategoryID();
            boolean _Result = mSQLiteDALCategory.updateCategory(pCategory,_Condition) ;
            boolean _Result2 = true;

            if (_Result && _Result2) {
                mSQLiteDALCategory.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false ;
        }
        finally {
            mSQLiteDALCategory.endTransaction();
        }
    }

    private List<Category> getCategory(String pCondition){
       return mSQLiteDALCategory.getCategory(pCondition) ;
    }

    public List<Category> getNoHideCategory(String pCondition){
        return mSQLiteDALCategory.getCategory(pCondition) ;
    }

    public List<Category> getCategoryByCategoryID(int pCategoryID){
        List<Category> _List = mSQLiteDALCategory.getCategory(" And CategoryID = " + pCategoryID)  ;
        if (_List.size() == 1){
            return _List ;
        }else {
            return null ;
        }

    }

    public List<Category> getCategoryListByCategoryID(String pCategoryID[]){
        List<Category> _List = new ArrayList<Category>() ;

        for (int i = 0 ; i < pCategoryID.length ; i++){
            _List.add((Category) getCategoryByCategoryID(Integer.valueOf(pCategoryID[i])))  ;
        }
        return _List ;
    }

    public boolean isExistCategoryByCategoryName(String pCategoryName,Integer pCategoryID){
        String _Condition = "And CategoryName = '"+pCategoryName+"'" ;
        if (pCategoryID!= null){
            _Condition += "And CategoryID <>"+pCategoryID ;
        }
        List _List = mSQLiteDALCategory.getCategory(_Condition) ;
        if (_List.size() > 0){
            return true ;
        }else {
            return false ;
        }
    }
    public boolean hideCategoryByCategoryID(int pCategoryID){
        String _Condition = "CategoryID = "+pCategoryID;
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("State",0);
        boolean _Result = mSQLiteDALCategory.updateCategory(_Condition,_ContentValues) ;
        if (_Result){
            return true ;
        }else {
            return false ;
        }
    }

    public int getNotHideCountByParentID(int pCategoryID){
        return mSQLiteDALCategory.getCount(TYPE_FLAG + " And ParentID = " + pCategoryID + "And State = 1") ;
    }
    public List getNotHideCategoryListByParentID(int pCategoryID){
        String _Condition = TYPE_FLAG + " And ParentID = " + pCategoryID + "And State = 1" ;
        return mSQLiteDALCategory.getCategory(_Condition) ;
    }
    public List getNotHideRootCategory(){
        String _Condition = TYPE_FLAG + " And ParentID = 0 ,State = 1" ;
        return mSQLiteDALCategory.getCategory(_Condition) ;
    }
    public ArrayAdapter getRootCategoryArrayAdapter(){
        List _List = getNotHideRootCategory() ;
        _List.add(0,"--请选择--") ;
        ArrayAdapter _Adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,_List) ;
        _Adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return _Adapter ;
    }



    public int getNoHideCount(){
        return mSQLiteDALCategory.getCount("And State = 1") ;
    }

    public boolean hideCategoryByPath(String pPath){
        String _Condition = "Path Like ' " + pPath + " % '" ;
        ContentValues _ContentValues =new ContentValues() ;
        _ContentValues.put("State",0);
        boolean _Result = mSQLiteDALCategory.updateCategory(_Condition,_ContentValues) ;
        if (_Result){
            return true ;
        }else {
            return false ;
        }
    }
}
