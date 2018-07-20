package com.example.android.todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class BootLoaderReciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        TaskOpenHelper openHelper =TaskOpenHelper.getInstance(context);
        SQLiteDatabase database = openHelper.getReadableDatabase();
       // String[] columns = {Contract.Task.COLUMN_ID,Contract.Task.TIME};
        Cursor cursor = database.query(Contract.Task.TABLE_NAME,null,  null,null,null,null,null);
        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndex(Contract.Task.COLUMN_ID));
            String date=cursor.getString(cursor.getColumnIndex(Contract.Task.DATE));
            String time=cursor.getString((cursor.getColumnIndex(Contract.Task.TIME)));

           // AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            String DateTime = date + " " + time;

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent intent1 = new Intent(context, MyReciever.class);
            intent1.putExtra("id",id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent1, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                Date mDate = sdf.parse(DateTime);
                long timeInMilliseconds = mDate.getTime();
                 Toast.makeText(context.getApplicationContext(),timeInMilliseconds+"",Toast.LENGTH_LONG).show();
               // alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
                // System.out.println("Date in milli :: " + timeInMilliseconds);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        cursor.close();


    }
}
