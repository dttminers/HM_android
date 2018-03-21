package com.hm.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hm.application.R;
import com.hm.application.model.DemoItem;
import com.hm.application.utils.CommonFunctions;

import java.util.List;

public class GridAdapter extends ArrayAdapter<DemoItem> {

    private final LayoutInflater layoutInflater;

    public GridAdapter(Context context, List<DemoItem> items) {
        super(context, 0, items);
        layoutInflater = LayoutInflater.from(context);
    }

    public GridAdapter(Context context) {
        super(context, 0);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DemoItem item = getItem(position);
        View v = layoutInflater.inflate(R.layout.single_imageview, parent, false);
//        TextView textView = (TextView) v.findViewById(R.id.textView);
//        textView.setText(item.getText());
        ImageView iv = (ImageView) v.findViewById(R.id.image_single);
        iv.setBackgroundColor(CommonFunctions.toGetRandomColor());
        iv.setBackgroundColor(item.getImg());
        return v;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }

    public void appendItems(List<DemoItem> newItems) {
        addAll(newItems);
        notifyDataSetChanged();
    }

    public void setItems(List<DemoItem> moreItems) {
        clear();
        appendItems(moreItems);
    }
}
