package com.example.android.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Sahil Sharma on 03-07-2018.
 */


public class TaskOpenHelper extends SQLiteOpenHelper {
  //  Context context;
    private static final int VERSION=1;
    public static final String DATABASE_NAME="TODO_db";
    private static TaskOpenHelper instance;
    public static TaskOpenHelper getInstance(Context context){
        if(instance==null){
            instance=new TaskOpenHelper(context.getApplicationContext());
        }
        return instance;
    }
    private TaskOpenHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
     //   this.context=context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

      //  Log.d("Log","in create");
      //  Toast.makeText(context,"in create",Toast.LENGTH_SHORT).show();
        String taskSql= " CREATE TABLE " + Contract.Task.TABLE_NAME  + " ( " +
                Contract.Task.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Contract.Task.COLUMN_NAME + " TEXT  , " +
                Contract.Task.DESCRIPTION + " TEXT  ) ";


        db.execSQL(taskSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
