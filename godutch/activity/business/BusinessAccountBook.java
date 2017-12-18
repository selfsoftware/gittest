package com.example.my.godutch.activity.business;

import android.content.ContentValues;
import android.content.Context;

import com.example.my.godutch.activity.business.businessbase.BusinessBase;
import com.example.my.godutch.activity.database.sqlitedal.SQLiteDALAccountBook;
import com.example.my.godutch.activity.model.AccountBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessAccountBook extends BusinessBase{
    private SQLiteDALAccountBook mSQLiteDALAccountBook ;
    public BusinessAccountBook(Context pContext) {
        super(pContext);
        mSQLiteDALAccountBook = new SQLiteDALAccountBook(pContext) ;
    }
    //商业
    public boolean insertAccountBook(AccountBook pAccountBook){
        mSQLiteDALAccountBook.beginTransaction();
        try {
            boolean _Result = mSQLiteDALAccountBook.insertAccountBook(pAccountBook);
            boolean _Result2 = true;
            if (pAccountBook.getIsDefault() == 1 && _Result) {
                _Result2 = setIsDefault(pAccountBook.getAccountBookID());
            }
            if (_Result && _Result2) {
                mSQLiteDALAccountBook.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false ;
        }
        finally {
            mSQLiteDALAccountBook.endTransaction();
        }


    }

    private boolean setIsDefault(int pAccountBookID) {
        String _Condition = " IsDefault = 1 "  ;
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("IsDefault",0);
        boolean _Result = mSQLiteDALAccountBook.updateAccountBook(_Condition,_ContentValues) ;
        _Condition = "AccountBookID = " + pAccountBookID ;
        _ContentValues.clear();
        _ContentValues.put("IsDefault",1);
        boolean _Result2 = mSQLiteDALAccountBook.updateAccountBook(_Condition,_ContentValues) ;
        if (_Result && _Result2){
            return true ;
        }else {
            return false ;
        }

    }

    public boolean deleteAccountBookByAccountBookID(AccountBook pAccountBook){
        String _Condition = " And AccountBookID = " + pAccountBook.getAccountBookID();
        boolean _Result = mSQLiteDALAccountBook.deleteAccountBook(_Condition) ;
        return _Result ;
    }

    public boolean updateAccountBookByAccountBookID(AccountBook pAccountBook){

        mSQLiteDALAccountBook.beginTransaction();
        try {
            String _Condition = " AccountBookID = " + pAccountBook.getAccountBookID();
            boolean _Result = mSQLiteDALAccountBook.updateAccountBook(pAccountBook,_Condition) ;
            boolean _Result2 = true;
            if (pAccountBook.getIsDefault() == 1 && _Result) {
                _Result2 = setIsDefault(pAccountBook.getAccountBookID());
            }
            if (_Result && _Result2) {
                mSQLiteDALAccountBook.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false ;
        }
        finally {
            mSQLiteDALAccountBook.endTransaction();
        }
    }

    private List<AccountBook> getAccountBook(String pCondition){
       return mSQLiteDALAccountBook.getAccountBook(pCondition) ;
    }

    public List<AccountBook> getNoHideAccountBook(String pCondition){
        return mSQLiteDALAccountBook.getAccountBook(pCondition) ;
    }

    public List<AccountBook> getAccountBookByAccountBookID(int pAccountBookID){
        List<AccountBook> _List = mSQLiteDALAccountBook.getAccountBook(" And AccountBookID = " + pAccountBookID)  ;
        if (_List.size() == 1){
            return _List ;
        }else {
            return null ;
        }

    }

    public List<AccountBook> getAccountBookListByAccountBookID(String pAccountBookID[]){
        List<AccountBook> _List = new ArrayList<AccountBook>() ;

        for (int i = 0 ; i < pAccountBookID.length ; i++){
            _List.add((AccountBook) getAccountBookByAccountBookID(Integer.valueOf(pAccountBookID[i])))  ;
        }
        return _List ;
    }

    public boolean isExistAccountBookByAccountBookName(String pAccountBookName,Integer pAccountBookID){
        String _Condition = "And AccountBookName = '"+pAccountBookName+"'" ;
        if (pAccountBookID!= null){
            _Condition += "And AccountBookID <>"+pAccountBookID ;
        }
        List _List = mSQLiteDALAccountBook.getAccountBook(_Condition) ;
        if (_List.size() > 0){
            return true ;
        }else {
            return false ;
        }
    }
    public boolean hideAccountBookByAccountBookID(int pAccountBookID){
        String _Condition = "AccountBookID = "+pAccountBookID;
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("State",0);
        boolean _Result = mSQLiteDALAccountBook.updateAccountBook(_Condition,_ContentValues) ;
        if (_Result){
            return true ;
        }else {
            return false ;
        }
    }
}
