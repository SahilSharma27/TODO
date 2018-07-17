package com.example.android.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReciever extends BroadcastReceiver {
    String name;
    String des;
    public static final String NAME = "title";
    public static final String DES = "body";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();


        if (data != null) {
            Object[] pdus = (Object[]) data.get("pdus");

            for (int i = 0; i < pdus.length; ++i) {

                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], "3gpp");
                name = smsMessage.getDisplayMessageBody();
                des = smsMessage.getMessageBody();
                // Intent intent1=new Intent(context,Main2Activity.class);
                // intent1.putExtra(NAME,name);
                // intent1.putExtra(DES,des);
                // context.startActivity(intent1);
                Toast.makeText(context, name, Toast.LENGTH_LONG).show();

            }

        }
    }
}
