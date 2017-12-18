package com.example.my.godutch.activity.database.sqlitedal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.database.base.SQLiteDALBase;
import com.example.my.godutch.activity.model.AccountBook;
import com.example.my.godutch.activity.utility.DateTools;

import java.util.List;


public class SQLiteDALAccountBook extends SQLiteDALBase {

    public SQLiteDALAccountBook(Context pContext) {
        super(pContext);
    }

    //封装数据库的（增）
    public boolean insertAccountBook(AccountBook pAccountBook) {
        ContentValues _ContentValues = createParams(pAccountBook);
        long _newID = getSQLiteDatabase(getContext()).insert(getTableNameAndPK()[0], null, _ContentValues);
        pAccountBook.setAccountBookID((int) _newID);
        return _newID > 0;
    }

    //封装数据库的（删）
    public boolean deleteAccountBook(String pCondition) {
        return Delete(getTableNameAndPK()[0], pCondition);
    }

    //封装数据库的（改）
    public boolean updateAccountBook(AccountBook pAccountBook, String pCondition) {
        ContentValues _ContentValues = createParams(pAccountBook);
        return getSQLiteDatabase(getContext()).update(getTableNameAndPK()[0], _ContentValues, "1=1" + pCondition, null) > 0;
    }

    public boolean updateAccountBook(String pCondition, ContentValues pContentValues) {
        return getSQLiteDatabase(getContext()).update("AccountBook", pContentValues, pCondition, null) > 0;
    }

    //封装数据库的（查-按条件群体查找）
    public List<AccountBook> getAccountBook(String pCondition) {
        String sqlText = "Select * From AccountBook Where 1=1" + pCondition;
        return getList(sqlText);
    }

    //实现父类数据库的（实体类查找的方法）
    @Override
    protected Object findModel(Cursor pCursor) {
        AccountBook _AccountBook = new AccountBook();
        _AccountBook.setAccountBookID(pCursor.getInt(pCursor.getColumnIndex("AccountBookID")));
        _AccountBook.setAccountBookName(pCursor.getString(pCursor.getColumnIndex("AccountBookName")));
        _AccountBook.setCreateDate(DateTools.StringToDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        _AccountBook.setState(pCursor.getInt(pCursor.getColumnIndex("State")));
        _AccountBook.setIsDefault(pCursor.getInt(pCursor.getColumnIndex("IsDefault")));
        return _AccountBook;
    }

    //实现父类数据库的（获取表名跟主键的方法）
    @Override
    protected String[] getTableNameAndPK() {
        return new String[]{"AccountBook", "AccountBookID"};
    }

    //初始化默认用户
    protected void initDefaultSQLiteAccountBooks(SQLiteDatabase db) {
        AccountBook _AccountBook = new AccountBook();
        String _AccountBookName[] = getContext().getResources().getStringArray(R.array.InitDefaultSQLiteAccountBooks);
        _AccountBook.setAccountBookName(_AccountBookName[0]);
        _AccountBook.setIsDefault(1);
        ContentValues _ContentValues = createParams(_AccountBook);
        db.insert(getTableNameAndPK()[0], null, _ContentValues);

    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _StringBuilder = new StringBuilder();
        _StringBuilder.append("Create TABLE AccountBook(");
        _StringBuilder.append("[AccountBookID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        _StringBuilder.append(",[AccountBookName] Varchar(10) NOT NULL");
        _StringBuilder.append(",[CreateDate] datetime NOT NULL");
        _StringBuilder.append(",[State] integer NOT NULL");
        _StringBuilder.append(",[IsDefault] integer NOT NULL");
        _StringBuilder.append(") ;");
        db.execSQL(_StringBuilder.toString());
        initDefaultSQLiteAccountBooks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db) {

    }

    //封装数据库操作的ContentValues
    public ContentValues createParams(AccountBook pAccountBook) {
        ContentValues _ContentValues = new ContentValues();
        _ContentValues.put("AccountBookName", pAccountBook.getAccountBookName());
        _ContentValues.put("CreateData", DateTools.formatDatetime(pAccountBook.getCreateDate()));
        _ContentValues.put("State", pAccountBook.getState());
        _ContentValues.put("IsDefault",pAccountBook.getIsDefault());
        return _ContentValues;
    }
}
