package com.example.goyal.todo1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class ActivityOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "activity";
    public ActivityOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String activitysql = " CREATE TABLE " + Contract.activity.TABLE_NAME + " ( " + Contract.activity.COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Contract.activity.COLOUMN_NAME + " TEXT , " + Contract.activity.COLOUMN_DATE + " DATE , " + Contract.activity.COLOUMN_TIME + " TIME , " + Contract.activity.COLOUMN_DESCRIPTION  +  " TEXT ) ";
        sqLiteDatabase.execSQL(activitysql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if (i1 > i) {
            sqLiteDatabase.execSQL( "ALTER TABLE activity ADD COLUMN date DATE DEFAULT 0");
            sqLiteDatabase.execSQL( "ALTER TABLE activity ADD COLUMN time TIME DEFAULT 0");
            sqLiteDatabase.execSQL( "ALTER TABLE activity ADD COLUMN description TEXT DEFAULT 0");
        }
    }
    
}
