package com.example.android.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    public static final String TITLE_KEY="title";
    public  static final String DESCRIPTION_KEY="description";

    EditText ed1;
    EditText ed2;
    Button btn;
    TextView t1;
    TextView t2;
    Button timeButton;
    Button dateButton;
    public static String Title;
    public static String Description;
    public int day, month, year, hour, minute;
    public static final int ADD_CODE=2;
    String timeTask;
    String dateTask;
    Intent intent;

    public static final String Date_id = "date";
    public static final String Time_id = "time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);
        ed1 = findViewById(R.id.title);
        ed2 = findViewById(R.id.description);
        btn = findViewById(R.id.save);
        t1 = findViewById(R.id.date);
        t2 = findViewById(R.id.time);
        dateButton = findViewById(R.id.setDate);
        timeButton = findViewById(R.id.setTime);
        //  dateTask=t1.getText().toString()timeTask=t2.getText().toString();
        Calendar clndr = Calendar.getInstance();
        day = clndr.get(Calendar.DAY_OF_MONTH);
        month = clndr.get(Calendar.MONTH);
        year=clndr.get(Calendar.YEAR);
        hour = clndr.get(Calendar.HOUR_OF_DAY);
        minute = clndr.get(Calendar.MINUTE);

        t1.setText(day + "/" + month + "/" + year);
        t2.setText(hour + ":" + minute);


    }

    public void setDateTime(View view) {

        int id = view.getId();
        if (id == R.id.setTime) {

            TimePickerDialog timePickerDialog = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    timeTask = hourOfDay + ":" + minute;
                    t2.setText(timeTask);
                    //     Toast.makeText(getApplicationContext(),hour+" "+minute,Toast.LENGTH_LONG).show();

                }
            }, hour, minute, false);

            timePickerDialog.show();
        }

        if (id == R.id.setDate) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    ++month;
                    dateTask = dayOfMonth + "/" + month + "/" + year;
                    t1.setText(dateTask);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }

    public void saveDetails(View view){

        intent=getIntent();
        Title= ed1.getText().toString();
        Description = ed2.getText().toString();
        intent.putExtra(TITLE_KEY,Title);
        intent.putExtra(DESCRIPTION_KEY,Description);
        intent.putExtra(Date_id, dateTask);
        intent.putExtra(Time_id, timeTask);
        setResult(ADD_CODE,intent);
        finish();
    }
}
