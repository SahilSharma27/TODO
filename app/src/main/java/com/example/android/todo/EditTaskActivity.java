package com.example.android.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {
    EditText ed1;
    EditText ed2;
    TextView t1;
    TextView t2;
    Button btn;
    Intent intent;
    public String Title;
    public String Description;
    public String Date;
    public String Time;
    public static final String TASK_TITLE="title";
    public static final String TASK_DESCRIPTION="description";
    public static final String TASK_DATE = "date";
    public static final String TASK_TIME = "time";
    public static final int EDITCODE=4;
    int day, month, year, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ed1=findViewById(R.id.editText);
        ed2=findViewById(R.id.editText2);
        t1 = findViewById(R.id.dateEdit);
        t2 = findViewById(R.id.timeEdit);
        btn=findViewById(R.id.btn);

        intent=getIntent();

        Title= intent.getStringExtra(DescriptionActivity.TITLE);
        Description = intent.getStringExtra(DescriptionActivity.DESCRIPTION);
        Date = intent.getStringExtra(DescriptionActivity.DATE);
        Time = intent.getStringExtra(DescriptionActivity.TIME);
        ed1.setText(Title);
        ed2.setText(Description);
        t1.setText(Date);
        t2.setText(Time);


    }
    // Calendar clndr=Calendar.getInstance();
    //day=clndr.get(Calendar.DAY_OF_MONTH);
    //month=clndr.get(Calendar.MONTH);
    //year=clndr.get(Calendar.YEAR);
    //hour=clndr.get(Calendar.HOUR_OF_DAY);
    //minute=clndr.get(Calendar.MINUTE);

    //  t1.setText(day+"/"+month+"/"+year);
    //   t2.setText(hour+":"+minute);*/


    public void editDateTime(View view) {
        Calendar clndr = Calendar.getInstance();
        day = clndr.get(Calendar.DAY_OF_MONTH);
        month = clndr.get(Calendar.MONTH);
        year = clndr.get(Calendar.YEAR);
        hour = clndr.get(Calendar.HOUR_OF_DAY);
        minute = clndr.get(Calendar.MINUTE);

        int id = view.getId();
        if (id == R.id.timeEdit) {

            TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    Time = hourOfDay + ":" + minute;
                    t2.setText(Time);

                }
            }, hour, minute, false);

            timePickerDialog.show();
        }

        if (id == R.id.dateEdit) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    ++month;
                    Date = dayOfMonth + "/" + month + "/" + year;
                    t1.setText(Date);
                }
            }, year, month, day);

            datePickerDialog.show();
        }
    }

   public void editDetails(View view){
       Title= ed1.getText().toString();
       Description = ed2.getText().toString();
       Date = t1.getText().toString();
       Time = t2.getText().toString();
       intent.putExtra(TASK_TITLE,Title);
       intent.putExtra(TASK_DESCRIPTION,Description);
       intent.putExtra(TASK_DATE, Date);
       intent.putExtra(TASK_TIME, Time);
       setResult(EDITCODE,intent);
       finish();
   }
}
