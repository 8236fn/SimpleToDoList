package com.blogspot.tiaotone.simpletodolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_MEMO = "memo";
    public static final String KEY_BGCOLOR = "bgColor";
    public static final String DATABASE_NAME = "Memos";
    public static final String TABLE_NAME = "tbMemo";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"("+
                KEY_ID + " integer PRIMARY KEY autoincrement," +
                KEY_DATE + "," +
                KEY_MEMO + "," +
                KEY_BGCOLOR +");" ;
        sqldb.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int oldVersion, int newVersion) {
        sqldb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqldb);
    }
}
