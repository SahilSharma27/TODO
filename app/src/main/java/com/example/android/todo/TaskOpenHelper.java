package com.example.android.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sahil Sharma on 03-07-2018.
 */

public class TaskOpenHelper extends SQLiteOpenHelper {
    public static final int VERSION=1;
    public static final String DATABASE_NAME="TODO_db";
    TaskOpenHelper(Context context){
        super(context,Contract.Task.TABLE_NAME,null,VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String taskSql="CREATE TABLE "+ Contract.Task.TABLE_NAME  + " (  " +
                Contract.Task.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Contract.Task.COLUMN_NAME + "TEXT , "+
                Contract.Task.DESCRIPTION + "TEXT )";
        db.execSQL(taskSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
