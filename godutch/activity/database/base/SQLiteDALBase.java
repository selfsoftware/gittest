package com.example.my.godutch.activity.database.base;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public  abstract class SQLiteDALBase implements SQLiteHelper.SQLiteDataTable{
    private Context mContext ;
    private SQLiteDatabase mSQLiteDatabase ;

    public SQLiteDALBase(Context pContext){
        mContext = pContext ;
    }
    protected Context getContext(){
        return mContext ;
    }

    public SQLiteDatabase getSQLiteDatabase(Context pContext){
        if (mSQLiteDatabase == null){
            mSQLiteDatabase = SQLiteHelper.getInstance(pContext).getWritableDatabase() ;
        }
        return mSQLiteDatabase ;
    }
    //数据库事物的封装
    public void beginTransaction(){
        mSQLiteDatabase.beginTransaction();
    }

    public void setTransactionSuccessful(){
        mSQLiteDatabase.setTransactionSuccessful();
    }

    public void endTransaction(){
        mSQLiteDatabase.endTransaction();
    }
    //查找数据库中的内容
    public int getCount(String pCondition){
        String _String[] = getTableNameAndPK() ;
        Cursor _Cursor = execSql("Select"+_String[1]+"From"+_String[0]+"Where 1 = 1"+pCondition) ;
        int _Count = _Cursor.getCount() ;
        _Cursor.close();
        return _Count ;
    }

    public int getCount(String pPK,String pTableName,String pCondition){
        Cursor _Cursor = execSql("Select"+pPK+"From"+pTableName+"Where 1 = 1"+pCondition) ;
        int _Count = _Cursor.getCount() ;
        _Cursor.close();
        return _Count ;
    }
    //获取数据库内容转换成List的集合
    protected List getList(String pSqlText){
        Cursor _Cursor = execSql(pSqlText) ;
        return cursorToList(_Cursor) ;
    }
    //选定数据库中的数据装换成一个对象
    protected abstract Object findModel(Cursor pCursor) ;

    //数据库内容转换成List集合
    protected List cursorToList(Cursor pCursor) {
        List _List = new ArrayList() ;
        while (pCursor.moveToNext()){
            Object _Object = findModel(pCursor) ;
            _List.add(_Object) ;
        }
        return _List ;
    }
    //封装delete方法
    protected Boolean Delete(String pTableName,String pCondition){

        return getSQLiteDatabase(getContext()).delete(pTableName,"1=1"+pCondition,null)>=0 ;
    }


    private Cursor execSql(String pSqlText) {
    return getSQLiteDatabase(getContext()).rawQuery(pSqlText,null) ;
    }

    protected abstract String[] getTableNameAndPK() ;


}
