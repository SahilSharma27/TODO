package com.example.android.todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    ListView listview;
    Intent intent;
    Intent intent2;
    Task_adapter adapter;
    Tasks task;
    FloatingActionButton fab;
    public static final int ADD_TASK_CODE=1;
    public static final int EDIT_TASK_CODE=2;
    public static  String CLICKED_TASK_TITLE="title";
    public static String CLICKED_TASK_DESCRIPTION="description";
    public static String CLICKED_TASK_DATE = "date";
    public static String CLICKED_TASK_TIME = "time";



   public ArrayList<Tasks> tasks=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.listview);
        fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_create_black_24dp);

        TaskOpenHelper openHelper= TaskOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase database=openHelper.getReadableDatabase();
        Cursor cursor=database.query(Contract.Task.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){

            String title=cursor.getString(cursor.getColumnIndex(Contract.Task.COLUMN_NAME));
            String dscrptn=cursor.getString(cursor.getColumnIndex(Contract.Task.DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndex(Contract.Task.DATE));
            String time = cursor.getString(cursor.getColumnIndex(Contract.Task.TIME));

            long id=cursor.getLong(cursor.getColumnIndex(Contract.Task.COLUMN_ID));
            Tasks task=new Tasks(title,dscrptn);
            task.setId(id);
            task.setDate(date);
            task.setTime(time);
            tasks.add(task);
        }
        cursor.close();
        adapter=new Task_adapter(this,tasks);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }




    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
        final Tasks T=tasks.get(position);
        final int pos=position;

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskOpenHelper openHelper=TaskOpenHelper.getInstance(getApplicationContext());
                SQLiteDatabase db=openHelper.getWritableDatabase();
                long id=T.getId();
                String[] selectionArgs = {id + ""};

                db.delete(Contract.Task.TABLE_NAME,Contract.Task.COLUMN_ID + " = ?",selectionArgs);

                tasks.remove(pos);
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "TODO Deleted..", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), MyReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, 0);
                pendingIntent.cancel();
                alarmManager.cancel(pendingIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        task=tasks.get(position);

        String T=task.getTitle();
        String D=task.getDescription();
        String dt = tasks.get(position).getDate();
        String tm = tasks.get(position).getTime();
        //  Toast.makeText(this,T,Toast.LENGTH_SHORT).show();
        intent2=new Intent(this,DescriptionActivity.class);
        intent2.putExtra(CLICKED_TASK_TITLE,T);
        intent2.putExtra(CLICKED_TASK_DESCRIPTION,D);
        intent2.putExtra(CLICKED_TASK_DATE, dt);
        intent2.putExtra(CLICKED_TASK_TIME, tm);
        startActivityForResult(intent2,EDIT_TASK_CODE);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, ADD_TASK_CODE);


        }

        return super.onOptionsItemSelected(item);
    }

    public void addTODO(View view) {
        intent = new Intent(this, Main2Activity.class);
        startActivityForResult(intent, ADD_TASK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_TASK_CODE) {
            if(resultCode==Main2Activity.ADD_CODE) {

                String title = data.getStringExtra(Main2Activity.TITLE_KEY);
                String des = data.getStringExtra(Main2Activity.DESCRIPTION_KEY);
                String date = data.getStringExtra(Main2Activity.Date_id);
                String time = data.getStringExtra(Main2Activity.Time_id);

                Tasks newTask = new Tasks(title, des);
                newTask.setTime(time);
                newTask.setDate(date);

                TaskOpenHelper openHelper = TaskOpenHelper.getInstance(this);
                SQLiteDatabase database=openHelper.getWritableDatabase();

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.Task.COLUMN_NAME,newTask.getTitle());
                contentValues.put(Contract.Task.DESCRIPTION,newTask.getDescription());
                contentValues.put(Contract.Task.DATE, newTask.getDate());
                contentValues.put(Contract.Task.TIME, newTask.getTime());
                  long id=database.insert(Contract.Task.TABLE_NAME,null,contentValues);
                // Toast.makeText(this,""+id,Toast.LENGTH_LONG).show();
                if (id > -1L) {
                        newTask.setId(id);
                        tasks.add(newTask);
                        adapter.notifyDataSetChanged();

                    }
               /* Calendar calendar = Calendar.getInstance();
                      calendar.set(2018,8,2);
                int month=Main2Activity.month;
                 int year=Main2Activity.year;
                 int day=Main2Activity.day;
                 int hour=Main2Activity.hour;
                 int min=Main2Activity.minute;
                 String DateTime=date+" "+time;
               calendar.set(year,month,day,hour,min);*/
                String DateTime = date + " " + time;

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(this, MyReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                try {
                    Date mDate = sdf.parse(DateTime);
                    long timeInMilliseconds = mDate.getTime();
                    //  Toast.makeText(getApplicationContext(),timeInMilliseconds+"",Toast.LENGTH_LONG).show();
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
                    // System.out.println("Date in milli :: " + timeInMilliseconds);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //long CurrentTimeInMS=calendar.getTimeMillis();
                // alarmManager.set(AlarmManager.RTC_WAKEUP,ti,pendingIntent);





            }

        }
       if(requestCode==EDIT_TASK_CODE) {
          if (resultCode == DescriptionActivity.EDIT_TASK_CODE) {


               String title = data.getStringExtra(DescriptionActivity.TITLE);
               String des = data.getStringExtra(DescriptionActivity.DESCRIPTION);
              String date = data.getStringExtra(DescriptionActivity.DATE);
              //    Toast.makeText(this,date,Toast.LENGTH_LONG).show();
              String time = data.getStringExtra(DescriptionActivity.TIME);
              TaskOpenHelper openHelper = TaskOpenHelper.getInstance(this);
              SQLiteDatabase database=openHelper.getWritableDatabase();
              ContentValues contentValues=new ContentValues();
              contentValues.put(Contract.Task.COLUMN_NAME,title);
              contentValues.put(Contract.Task.DESCRIPTION,des);
              contentValues.put(Contract.Task.DATE, date);
              contentValues.put(Contract.Task.TIME, time);
              database.update(Contract.Task.TABLE_NAME,contentValues," id="+task.getId(),null);
              String DateTime = date + " " + time;

              AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
              Intent intent = new Intent(this, MyReciever.class);
              PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent, 0);
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
              try {
                  Date mDate = sdf.parse(DateTime);
                  long timeInMilliseconds = mDate.getTime();
                  //  Toast.makeText(getApplicationContext(),timeInMilliseconds+"",Toast.LENGTH_LONG).show();
                  alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
                  // System.out.println("Date in milli :: " + timeInMilliseconds);

              } catch (ParseException e) {
                  e.printStackTrace();
              }


              task.setTitle(title);
               task.setDescription(des);
              task.setDate(date);
              task.setTime(time);
               adapter.notifyDataSetChanged();
          }
       }



    }
}
