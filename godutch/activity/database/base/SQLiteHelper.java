package com.example.my.godutch.activity.database.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.my.godutch.activity.database.sqlitedal.SQLiteDALUser;
import com.example.my.godutch.activity.utility.Reflection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/15.
 */

public class SQLiteHelper extends SQLiteOpenHelper{
    private static SQLiteDataBaseConfig SQLITE_DATABASE_CONFIG ;
    private Context mContext ;
    private static SQLiteHelper INSTANCE ;
    private Reflection mReflection ;

    public interface SQLiteDataTable{
        public void onCreate(SQLiteDatabase db) ;

        public void onUpgrade(SQLiteDatabase db) ;
    }
    private SQLiteHelper(Context pContext){
        super(pContext,SQLITE_DATABASE_CONFIG.getDatabaseName(),null,SQLITE_DATABASE_CONFIG.getVersion());
        mContext = pContext ;
    }
    //单例的实现
    public static SQLiteHelper getInstance(Context pContext){
        if ( INSTANCE == null){
            SQLITE_DATABASE_CONFIG = SQLITE_DATABASE_CONFIG.getInstance(pContext) ;
            INSTANCE = new SQLiteHelper(pContext) ;

        }
        return INSTANCE ;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        mReflection = new Reflection() ;
        ArrayList<String> _arrayList = SQLITE_DATABASE_CONFIG.getTables() ;
        for (int i = 0; i<_arrayList.size(); i++){
            try {
                SQLiteDataTable _SQLiteDataTable = (SQLiteDataTable)mReflection.newInstance(_arrayList.get(i),new Object[]{mContext},new Class[]{Context.class}) ;
                _SQLiteDataTable.onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
