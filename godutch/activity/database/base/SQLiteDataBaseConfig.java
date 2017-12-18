package com.example.my.godutch.activity.database.base;

import android.content.Context;

import com.example.my.godutch.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/15.
 */

public class SQLiteDataBaseConfig {
    private static final String DATABASE_NAME = "GoDutchDataBase" ;
    private static final int VERSION = 1 ;
    private static  SQLiteDataBaseConfig INSTANCE ;
    private static Context mContext ;

    private SQLiteDataBaseConfig() {

    }
    public static SQLiteDataBaseConfig getInstance(Context pContext){
        if (INSTANCE == null){
            INSTANCE = new SQLiteDataBaseConfig() ;
            mContext = pContext ;
        }
        return INSTANCE ;
    }

    public String getDatabaseName (){
        return DATABASE_NAME ;
    }

    public int getVersion(){
        return VERSION ;
    }

    public ArrayList<String> getTables(){
        ArrayList<String> _arrayList = new ArrayList<>() ;
        String[] _SQLiteDALClassName = mContext.getResources().getStringArray(R.array.SQLiteDALClassName) ;
        String _PackagePath = mContext.getPackageName() + ".database.sqlitedal.";
        for (int i = 0; i < _SQLiteDALClassName.length; i++){
            _arrayList.add(_PackagePath + _SQLiteDALClassName[i]) ;
        }
        return _arrayList ;
    }
}
