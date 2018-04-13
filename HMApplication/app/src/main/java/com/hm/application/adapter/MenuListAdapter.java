package com.hm.application.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.utils.HmFonts;

public class MenuListAdapter extends BaseExpandableListAdapter {

    //Data
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    //ChildView
    private String childText;
    private TextView txtListChild;

    // GroupView
    private String headerTitle;
    private ImageView iv;
    private TextView lblListHeader;
    private LinearLayout mllListGrp;

    public MenuListAdapter(Context _context, List<String> _listDataHeader, HashMap<String, List<String>> _listDataChild) {
        context = _context;
        listDataHeader = _listDataHeader;
        listDataChild = _listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        txtListChild = convertView.findViewById(R.id.lblListItem);
        txtListChild.setTypeface(HmFonts.getRobotoRegular(context));
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        iv = convertView.findViewById(R.id.ivIcon);
        if (groupPosition == 0) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.travels));
        } else if (groupPosition == 1) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.travels));
        } else if (groupPosition == 2) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_shop));
        } else if (groupPosition == 3) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_entrepreneur));
        } else if (groupPosition == 4) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_social));
        } else if (groupPosition == 5) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_service));
        } else if (groupPosition == 10) {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_logout));
        } else {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hm));
        }


        lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(HmFonts.getRobotoRegular(context));
        lblListHeader.setText(headerTitle);

        mllListGrp = convertView.findViewById(R.id.ll_list_grp);

        if (isExpanded) {
            mllListGrp.setBackground(context.getResources().getDrawable(R.drawable.sel));
            lblListHeader.setTypeface(HmFonts.getRobotoBold(context));
        } else {
            mllListGrp.setBackgroundColor(context.getResources().getColor(R.color.white));
            lblListHeader.setTypeface(HmFonts.getRobotoRegular(context));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}