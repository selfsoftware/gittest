package com.example.my.godutch.activity.database.sqlitedal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.database.base.SQLiteDALBase;
import com.example.my.godutch.activity.model.Category;
import com.example.my.godutch.activity.utility.DateTools;

import java.util.List;


public class SQLiteDALCategory extends SQLiteDALBase {

    public SQLiteDALCategory(Context pContext) {
        super(pContext);
    }

    //封装数据库的（增）
    public boolean insertCategory(Category pCategory) {
        ContentValues _ContentValues = createParams(pCategory);
        long _newID = getSQLiteDatabase(getContext()).insert(getTableNameAndPK()[0], null, _ContentValues);
        pCategory.setCategoryID((int) _newID);
        return _newID > 0;
    }

    //封装数据库的（删）
    public boolean deleteCategory(String pCondition) {
        return Delete(getTableNameAndPK()[0], pCondition);
    }

    //封装数据库的（改）
    public boolean updateCategory(Category pCategory, String pCondition) {
        ContentValues _ContentValues = createParams(pCategory);
        return getSQLiteDatabase(getContext()).update(getTableNameAndPK()[0], _ContentValues, "1=1" + pCondition, null) > 0;
    }

    public boolean updateCategory(String pCondition, ContentValues pContentValues) {
        return getSQLiteDatabase(getContext()).update("Category", pContentValues, pCondition, null) > 0;
    }

    //封装数据库的（查-按条件群体查找）
    public List<Category> getCategory(String pCondition) {
        String sqlText = "Select * From Category Where 1=1" + pCondition;
        return getList(sqlText);
    }

    //实现父类数据库的（实体类查找的方法）
    @Override
    protected Object findModel(Cursor pCursor) {
        Category _Category = new Category();
        _Category.setCategoryID(pCursor.getInt(pCursor.getColumnIndex("CategoryID")));
        _Category.setCategoryName(pCursor.getString(pCursor.getColumnIndex("CategoryName")));
        _Category.setCreateDate(DateTools.StringToDate(pCursor.getString(pCursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        _Category.setState(pCursor.getInt(pCursor.getColumnIndex("State")));
        _Category.setParentID(pCursor.getInt(pCursor.getColumnIndex("ParentID")));
        _Category.setTypeFlag(pCursor.getString(pCursor.getColumnIndex("TypeFlag")));
        _Category.setPath(pCursor.getString(pCursor.getColumnIndex("Path")));
        return _Category;
    }

    //实现父类数据库的（获取表名跟主键的方法）
    @Override
    protected String[] getTableNameAndPK() {
        return new String[]{"Category", "CategoryID"};
    }

    //初始化默认用户
    protected void initDefaultSQLiteCategory(SQLiteDatabase db) {
        Category _Category = new Category();
        _Category.setTypeFlag(getContext().getString(R.string.PayoutTypeFlag));
        _Category.setParentID(0);
        _Category.setPath("");
        String _CategoryName[] = getContext().getResources().getStringArray(R.array.InitDefaultSQLiteCategorys);
        for (int i = 0 ; i < _CategoryName.length ; i++){
            _Category.setCategoryName(_CategoryName[i]);
            ContentValues _ContentValues = createParams(_Category);
            Long _NewID = db.insert("Category", null, _ContentValues);
            _Category.setPath(_NewID.intValue() + ".");
            _ContentValues = createParams(_Category) ;
            db.update("Category",_ContentValues,"CategoryID = " + _NewID.intValue(),null) ;
        }




    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder _StringBuilder = new StringBuilder();
        _StringBuilder.append("Create TABLE Category(");
        _StringBuilder.append("[CategoryID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        _StringBuilder.append(",[CategoryName] Varchar(10) NOT NULL");
        _StringBuilder.append(",[CreateDate] datetime NOT NULL");
        _StringBuilder.append(",[State] integer NOT NULL");
        _StringBuilder.append(",[ParentID] integer NOT NULL");
        _StringBuilder.append(",[TypeFlag] varchar(20) NOT NULL");
        _StringBuilder.append(",[Path] text NOT NULL");
        _StringBuilder.append(") ;");
        db.execSQL(_StringBuilder.toString());
        initDefaultSQLiteCategory(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db) {

    }

    //封装数据库操作的ContentValues
    public ContentValues createParams(Category pCategory) {
        ContentValues _ContentValues = new ContentValues();
        _ContentValues.put("CategoryName", pCategory.getCategoryName());
        _ContentValues.put("CreateData", DateTools.formatDatetime(pCategory.getCreateDate()));
        _ContentValues.put("State", pCategory.getState());
        _ContentValues.put("ParentID",pCategory.getParentID());
        _ContentValues.put("TypeFlag",pCategory.getTypeFlag());
        _ContentValues.put("Path",pCategory.getPath());
        return _ContentValues;
    }
}
