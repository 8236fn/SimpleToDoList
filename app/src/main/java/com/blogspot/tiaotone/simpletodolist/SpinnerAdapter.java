package com.blogspot.tiaotone.simpletodolist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter{
    public ArrayList<ItemData> colorList;
    public LayoutInflater inflater;
    public Context context;

    public SpinnerAdapter(@NonNull Context context, ArrayList<ItemData> colorList) {
        this.colorList = colorList;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int position) {
        return colorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return colorList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemData itemData = (ItemData) getItem(position);
        View view = inflater.inflate(R.layout.color_view,null);
        ImageView colorTicket = view.findViewById(R.id.colorTicket);
        TextView colorName = view.findViewById(R.id.colorName);
        colorTicket.setBackgroundColor(Color.parseColor(itemData.code));
        colorName.setText(itemData.name);
        return view;
    }
}
