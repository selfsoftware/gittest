package com.example.my.godutch.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my.godutch.R;
import com.example.my.godutch.activity.adapter.base.AdapterBase;
import com.example.my.godutch.activity.business.BusinessCategory;
import com.example.my.godutch.activity.business.BusinessUser;
import com.example.my.godutch.activity.model.Category;
import com.example.my.godutch.activity.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class AdapterCategory extends BaseExpandableListAdapter{
    private class GroupHolder{
        TextView tvGroupName ;
        TextView tvCount ;

    }
    private class ChildHolder{
        TextView tvChildName ;
    }

    private Context mContext ;
    private List mList ;
    private BusinessCategory mBusinessCategory ;
    public List mChildCountOfGroup ;

    public AdapterCategory(Context pContext) {
        mBusinessCategory = new BusinessCategory(pContext) ;
        mContext = pContext ;
        List _List = mBusinessCategory.getNoHideCategory(" And State = 1");
        mChildCountOfGroup = new ArrayList() ;

    }
    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Category _Category = (Category) getGroup(groupPosition);
        List _List = mBusinessCategory.getNotHideCategoryListByParentID(_Category.getCategoryID()) ;
        return _List.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Category _Category = (Category) getGroup(groupPosition);
        List _List = mBusinessCategory.getNotHideCategoryListByParentID(_Category.getCategoryID()) ;
        return _List.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder mGroupHolder ;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.category_group_list_item,null) ;
            mGroupHolder = new GroupHolder() ;
            mGroupHolder.tvGroupName = (TextView) convertView.findViewById(R.id.tvCategoryGroupName);
            mGroupHolder.tvCount = (TextView) convertView.findViewById(R.id.tvCategoryGroupCount);
            convertView.setTag(mGroupHolder);
        }else {
            mGroupHolder = (GroupHolder) convertView.getTag();
        }
        Category mCategory = (Category) getGroup(groupPosition);
        mGroupHolder.tvGroupName.setText(mCategory.getCategoryName());
        int _Count = mBusinessCategory.getNotHideCountByParentID(mCategory.getCategoryID()) ;
        mGroupHolder.tvCount.setText(mContext.getString(R.string.TextOfChildCategoryCount,new Object[]{_Count}));
        mChildCountOfGroup.add(_Count) ;

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder mChildHolder ;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.category_children_list_item,null) ;
            mChildHolder = new ChildHolder() ;
            mChildHolder.tvChildName = (TextView) convertView.findViewById(R.id.tvCategoryChildrenName);
            convertView.setTag(mChildHolder);
        }else {
            mChildHolder = (ChildHolder) convertView.getTag();

        }
        Category _Category = (Category) getChild(groupPosition,childPosition);
        mChildHolder.tvChildName.setText(_Category.getCategoryName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public Integer getChildCountOfGroup(int groupPosition){
        return (Integer) mChildCountOfGroup.get(groupPosition);
    }





}
