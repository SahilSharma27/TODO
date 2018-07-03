package com.example.android.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    public static final String TITLE_KEY="title";
    public  static final String DESCRIPTION_KEY="description";

    EditText ed1;
    EditText ed2;
    Button btn;
    //TextView t1;
    //TextView t2;
    //Button timeButton;
   // Button dateButton;
    public static String Title;
    public static String Description;
   // int day ,month,year,hour,minute;
    public static final int ADD_CODE=2;
    Intent intent;
    //private static final int Date_id = 0;
    //private static final int Time_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);
        ed1 = findViewById(R.id.title);
        ed2 = findViewById(R.id.description);
        btn = findViewById(R.id.save);
        //t1 = findViewById(R.id.date);
       // t2 = findViewById(R.id.time);
       /* dateButton = findViewById(R.id.setDate);
        timeButton = findViewById(R.id.setTime);
        Calendar clndr=Calendar.getInstance();
        day=clndr.get(Calendar.DAY_OF_MONTH);
        month=clndr.get(Calendar.MONTH);
        year=clndr.get(Calendar.YEAR);
        hour=clndr.get(Calendar.HOUR_OF_DAY);
        minute=clndr.get(Calendar.MINUTE);

        t1.setText(day+"/"+month+"/"+year);
        t2.setText(hour+":"+minute);*/

    }

    public void saveDetails(View view){
        intent=getIntent();
        Title= ed1.getText().toString();
        Description = ed2.getText().toString();
        intent.putExtra(TITLE_KEY,Title);
        intent.putExtra(DESCRIPTION_KEY,Description);
        setResult(ADD_CODE,intent);
        finish();
    }
}
