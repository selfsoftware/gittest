package com.example.my.godutch.activity.database.sqlitedal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.service.notification.Condition;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.database.base.SQLiteDALBase;
import com.example.my.godutch.activity.model.User;
import com.example.my.godutch.activity.utility.DateTools;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class SQLiteDALUser extends SQLiteDALBase {

    public SQLiteDALUser(Context pContext) {
        super(pContext);
    }
    //封装数据库的（增）
    public boolean insertUser(User pUser){
        ContentValues _ContentValues = createParams(pUser) ;
        long _newID = getSQLiteDatabase(getContext()).insert(getTableNameAndPK()[0],null,_ContentValues) ;
        pUser.setUserID((int)_newID);
        return _newID > 0 ;
    }
    //封装数据库的（删）
    public boolean deleteUser(String pCondition){
        return Delete(getTableNameAndPK()[0],pCondition);
    }
    //封装数据库的（改）
    public boolean updateUser(User pUser,String pCondition){
        ContentValues _ContentValues = createParams(pUser) ;
        return getSQLiteDatabase(getContext()).update(getTableNameAndPK()[0],_ContentValues,"1=1"+pCondition,null) > 0 ;
    }
    public boolean updateUser(String pCondition,ContentValues pContentValues){
        return getSQLiteDatabase(getContext()).update("User",pContentValues,pCondition,null) > 0 ;
    }
    //封装数据库的（查-按条件群体查找）
    public List<User> getUser(String pCondition){
        String sqlText = "Select * From User Where 1=1"+pCondition ;
        return getList(sqlText) ;
    }
    //实现父类数据库的（实体类查找的方法）
    @Override
    protected Object findModel(Cursor pCursor) {
        User _User = new User() ;
        _User.setUserID(pCursor.getInt(pCursor.getColumnIndex("UserID")));
        _User.setUserName(pCursor.getString(pCursor.getColumnIndex("UserName")));
        _User.setCreateDate(DateTools.StringToDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")),"yyyy-MM-dd HH:mm:ss"));
        _User.setState(pCursor.getInt(pCursor.getColumnIndex("State")));
        return _User;
    }

    //实现父类数据库的（获取表名跟主键的方法）
    @Override
    protected String[] getTableNameAndPK() {
        return new String[]{"User","UserID"};
    }
    //初始化默认用户
    protected void initDefaultSQLiteUsers(SQLiteDatabase db){
        User _User = new User();

        String _UserName[] = getContext().getResources().getStringArray(R.array.InitDefaultSQLiteUsers) ;
        for (int i = 0 ;i < _UserName[i].length();i++){
            _User.setUserName(_UserName[i]);
            ContentValues _ContentValues = createParams(_User) ;
            db.insert(getTableNameAndPK()[0],null,_ContentValues) ;
        }
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _StringBuilder = new StringBuilder() ;
        _StringBuilder.append("Create TABLE User(") ;
        _StringBuilder.append("[UserID] integer PRIMARY KEY AUTOINCREMENT NOT NULL") ;
        _StringBuilder.append(",[UserName] Varchar(10) NOT NULL") ;
        _StringBuilder.append(",[CreateDate] datetime NOT NULL") ;
        _StringBuilder.append(",[State] integer NOT NULL") ;
        _StringBuilder.append(") ;");
        db.execSQL(_StringBuilder.toString());
        initDefaultSQLiteUsers(db) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db) {

    }

    //封装数据库操作的ContentValues
    public ContentValues createParams(User pUser){
        ContentValues _ContentValues = new ContentValues() ;
        _ContentValues.put("UserName",pUser.getUserName());
        _ContentValues.put("CreateData", DateTools.formatDatetime(pUser.getCreateDate()));
        _ContentValues.put("State",pUser.getState());
        return _ContentValues ;
    }
}
