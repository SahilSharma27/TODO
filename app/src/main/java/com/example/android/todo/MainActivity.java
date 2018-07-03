package com.example.android.todo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    ListView listview;
    Intent intent;
    Intent intent2;
    Task_adapter adapter;
    Tasks task;
    public static final int ADD_TASK_CODE=1;
    public static final int EDIT_TASK_CODE=2;
    public static  String CLICKED_TASK_TITLE="title";
    public static String CLICKED_TASK_DESCRIPTION="description";


   public ArrayList<Tasks> tasks=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.listview);

        TaskOpenHelper openHelper=new TaskOpenHelper(this);
        SQLiteDatabase database=openHelper.getReadableDatabase();
        Cursor cursor=database.query(Contract.Task.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){

            String title=cursor.getString(cursor.getColumnIndex(Contract.Task.COLUMN_NAME));
            String dscrptn=cursor.getString(cursor.getColumnIndex(Contract.Task.DESCRIPTION));
            Tasks task=new Tasks(title,dscrptn);
            tasks.add(task);
        }
        adapter=new Task_adapter(this,tasks);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setOnItemLongClickListener(this);
    }




    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final int pos=position;

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Confirm Delete");
        builder.setCancelable(false);
        builder.setMessage("Do you really want to delete ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                tasks.remove(pos);
                adapter.notifyDataSetChanged();
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
        intent2=new Intent(this,DescriptionActivity.class);
        intent2.putExtra(CLICKED_TASK_TITLE,T);
        intent2.putExtra(CLICKED_TASK_DESCRIPTION,D);
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
        if(id==R.id.addItem){
            intent=new Intent(this,Main2Activity.class);
            startActivityForResult(intent,ADD_TASK_CODE);


           
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_TASK_CODE) {
            if(resultCode==Main2Activity.ADD_CODE) {

                String title = data.getStringExtra(Main2Activity.TITLE_KEY);
                String des = data.getStringExtra(Main2Activity.DESCRIPTION_KEY);
                Tasks newTask = new Tasks(title, des);
                TaskOpenHelper openHelper = new TaskOpenHelper(this);
                SQLiteDatabase database=openHelper.getWritableDatabase();

                ContentValues contentValues=new ContentValues();
                contentValues.put(Contract.Task.COLUMN_NAME,newTask.getTitle());
                contentValues.put(Contract.Task.DESCRIPTION,newTask.getDescription());
                database.insert(Contract.Task.TABLE_NAME,null,contentValues);
                tasks.add(newTask);
                adapter.notifyDataSetChanged();

            }

        }
       if(requestCode==EDIT_TASK_CODE) {
          if (resultCode == DescriptionActivity.EDIT_TASK_CODE) {


               String title = data.getStringExtra(DescriptionActivity.TITLE);
               String des = data.getStringExtra(DescriptionActivity.DESCRIPTION);
               task.setTitle(title);
               task.setDescription(des);
               adapter.notifyDataSetChanged();
          }
       }



    }
}
