package com.example.my.godutch.activity.business;

import android.content.ContentValues;
import android.content.Context;
import android.service.notification.Condition;

import com.example.my.godutch.activity.business.businessbase.BusinessBase;
import com.example.my.godutch.activity.database.sqlitedal.SQLiteDALUser;
import com.example.my.godutch.activity.model.User;

import java.util.ArrayList;
import java.util.List;

import dalvik.bytecode.OpcodeInfo;

/**
 * Created by Administrator on 2017/8/18.
 */

public class BusinessUser extends BusinessBase{
    private SQLiteDALUser mSQLiteDALUser ;
    public BusinessUser(Context pContext) {
        super(pContext);
        mSQLiteDALUser = new SQLiteDALUser(pContext) ;
    }
    //商业
    public boolean insertUser(User pUser){
        boolean _Result = mSQLiteDALUser.insertUser(pUser) ;
        return _Result ;
    }

    public boolean deleteUserByUserID(User pUser){
        String _Condition = " And UserID = " + pUser.getUserID();
        boolean _Result = mSQLiteDALUser.deleteUser(_Condition) ;
        return _Result ;
    }

    public boolean updateUserByUserID(User pUser){
        String _Condition = " UserID = " + pUser.getUserID();
        boolean _Result = mSQLiteDALUser.updateUser(pUser,_Condition) ;
        return _Result ;
    }

    private List<User> getUser(String pCondition){
       return mSQLiteDALUser.getUser(pCondition) ;
    }

    public List<User> getNoHideUser(String pCondition){
        return mSQLiteDALUser.getUser(pCondition) ;
    }

    public List<User> getUserByUserID(int pUserID){
        List<User> _List = mSQLiteDALUser.getUser(" And UserID = " + pUserID)  ;
        if (_List.size() == 1){
            return _List ;
        }else {
            return null ;
        }

    }

    public List<User> getUserListByUserID(String pUserID[]){
        List<User> _List = new ArrayList<User>() ;

        for (int i = 0 ; i < pUserID.length ; i++){
            _List.add((User) getUserByUserID(Integer.valueOf(pUserID[i])))  ;
        }
        return _List ;
    }

    public boolean isExistUserByUserName(String pUserName,Integer pUserID){
        String _Condition = "And UserName = '"+pUserName+"'" ;
        if (pUserID!= null){
            _Condition += "And UserID <>"+pUserID ;
        }
        List _List = mSQLiteDALUser.getUser(_Condition) ;
        if (_List.size() > 0){
            return true ;
        }else {
            return false ;
        }
    }
    public boolean hideUserByUserID(int pUserID){
        String _Condition = "UserID = "+pUserID;
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("State",0);
        boolean _Result = mSQLiteDALUser.updateUser(_Condition,_ContentValues) ;
        if (_Result){
            return true ;
        }else {
            return false ;
        }
    }
}
