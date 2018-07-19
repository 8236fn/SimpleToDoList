package com.blogspot.tiaotone.simpletodolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

//協助產生listview的動態介面
public class ListAdapter extends CursorAdapter{
    public LayoutInflater inflater;
    public ListAdapter(Context context, Cursor c) {
        super(context, c, 0);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    //利用inflate取得itemlist的layout
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.item_view,parent,false);
    }

    @Override

    public void bindView(View view, Context context, Cursor cursor) {
        //連結item_view
        TextView date = view.findViewById(R.id.tvDate);
        TextView memo = view.findViewById(R.id.tvMemo);
        LinearLayout layout = view.findViewById(R.id.bgMemo);

        date.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        memo.setText(cursor.getString(cursor.getColumnIndexOrThrow("memo")));
        layout.setBackgroundColor(Color.parseColor("#"+cursor.getString(3)));
    }
}
