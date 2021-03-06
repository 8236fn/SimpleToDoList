package com.blogspot.tiaotone.simpletodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DbAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_MEMO = "memo";
    public static final String KEY_BGCOLOR = "bgColor";
    public static final String TABLE_NAME = "tbMemo";
    private DbHelper dbHelper;
    private SQLiteDatabase mDb;
    private Context mCtx;
    private ContentValues values;

    public DbAdapter(Context mCtx){
        this.mCtx = mCtx;
        open();
    }
    //開啟連結資料庫
    public void open(){
        dbHelper = new DbHelper(mCtx);
        mDb = dbHelper.getWritableDatabase();
    }
    //新增
    public long createMemo(String date, String memo, String bgColor){
        try {//資料進入ContentValues 再進資料庫
            values = new ContentValues();
            values.put(KEY_DATE,date);
            values.put(KEY_MEMO,memo);
            values.put(KEY_BGCOLOR,bgColor);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx,"資料已經新增",Toast.LENGTH_LONG).show();
        }
        return mDb.insert(TABLE_NAME,null,values);
    }
    //修改
    public long updateMemo(int id, String date, String memo, String bgColor){
        try{//資料進入ContentValues 再進資料庫
            values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_MEMO,memo);
            values.put(KEY_BGCOLOR,bgColor);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx,"資料已經更新",Toast.LENGTH_LONG).show();
        }
        return mDb.update(TABLE_NAME,values,"_id="+id,null);
    }
    //刪除
    public boolean deleteMemo(int id){
        String[] args = {Integer.toString(id)};
        mDb.delete(TABLE_NAME,"_id=?",args);
        Toast.makeText(mCtx,"資料已經刪除",Toast.LENGTH_LONG).show();
        return true;
    }
    //傳資料給listview
    public Cursor listshow(){
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,KEY_DATE,KEY_MEMO,KEY_BGCOLOR},
                null,null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        return cursor;
    }
    //查詢
    public Cursor queryById(int id){
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{KEY_ID,KEY_DATE,KEY_MEMO,KEY_BGCOLOR},
                KEY_ID + "=" + id,null,null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
