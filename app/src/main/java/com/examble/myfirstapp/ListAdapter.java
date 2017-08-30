package com.examble.myfirstapp;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_SECTION = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    protected Context context;
    protected ArrayList<ListItem> itemArray;
    private static final String TAG = "print";

    public ListAdapter(Context context, ArrayList<ListItem> items) {
        this.context = context;
        this.itemArray = items;
    }

    public class ViewHolder {
        private ListItem listItem;
        private ImageView editItem;
        private TextView itemName;
        private TextView itemInfo;

        public ViewHolder(View view) {
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemInfo = (TextView) view.findViewById(R.id.item_info);
        }

        public void bindData(ListItem item) {
            listItem = item;
            itemName.setText(item.getName());
            itemInfo.setText(item.getInfo());
        }
    }

    public View getListView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.bindData(itemArray.get(position));

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getListView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return itemArray.size();
    }

    @Override
    public ListItem getItem(int position) {
        return itemArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
