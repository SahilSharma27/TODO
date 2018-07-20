package com.example.android.todo;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static android.Manifest.permission_group.CALENDAR;
import static android.content.Context.MODE_PRIVATE;

public class SmsReciever extends BroadcastReceiver {
    String name;
    String des;
    // long timeStamp;
    String DateTask;
    String TimeTask;

    // public static final String NAME = "title";
    //public static final String DES = "body";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Boolean value = pref.getBoolean("SwitchON", false);
        Bundle data = intent.getExtras();

        if (value) {
            if (data != null) {
                Object[] pdus = (Object[]) data.get("pdus");

                for (int i = 0; i < pdus.length; ++i) {

                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                    name = smsMessage.getDisplayOriginatingAddress();
                    Log.d("SMsReciever", name);
                    des = smsMessage.getDisplayMessageBody();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(smsMessage.getTimestampMillis());

                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                    int hour = calendar.get(calendar.HOUR_OF_DAY);
                    int min = calendar.get(calendar.MINUTE);
                    TimeTask = hour + ":" + min;
                    DateTask = day + "/" + month + "/" + year;
                    Tasks smsTask = new Tasks(name, des);
                    smsTask.setTime(TimeTask);
                    smsTask.setDate(DateTask);

                    TaskOpenHelper expensesOpenHelper = TaskOpenHelper.getInstance(context);
                    SQLiteDatabase database = expensesOpenHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(Contract.Task.COLUMN_NAME, name);
                    contentValues.put(Contract.Task.DESCRIPTION, des);
                    contentValues.put(Contract.Task.DATE, smsTask.getDate());
                    contentValues.put(Contract.Task.TIME, smsTask.getTime());

                    long id = database.insert(Contract.Task.TABLE_NAME, null, contentValues);
                    if (id > -1L) {
                        Toast.makeText(context, "Todo Added!", Toast.LENGTH_SHORT).show();
                    }

                    // timeStamp=smsMessage.getTimestampMillis();
                    // DateFormat.format(" MMMM dd, yyyy ", new Date(timeStamp));

                    // Intent intent1=new Intent(context,Main2Activity.class);
                    // intent1.putExtra(NAME,name);
                    // intent1.putExtra(DES,des);
                    // context.startActivity(intent1);
                    //  Toast.makeText(context, name, Toast.LENGTH_LONG).show();

                }

            }
        }
    }
}
