package com.example.android.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    Intent intent;
    String title, description, date, time;
   // Button btn ;
    public static final String TITLE="title";
    public static final String DESCRIPTION="description";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final int EDIT_TASK_CODE=1;
    //  public boolean isEdited=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        t1=findViewById(R.id.Title);
        t2=findViewById(R.id.Description);
        t3 = findViewById(R.id.date);
        t4 = findViewById(R.id.time);
      //  btn=findViewById(R.id.back);
      //  btn.setVisibility(View.GONE);

      //  btn.setEnabled(false);
        intent=getIntent();
         title=intent.getStringExtra(MainActivity.CLICKED_TASK_TITLE);
         description=intent.getStringExtra(MainActivity.CLICKED_TASK_DESCRIPTION);
        date = intent.getStringExtra(MainActivity.CLICKED_TASK_DATE);
        time = intent.getStringExtra(MainActivity.CLICKED_TASK_TIME);
        t1.setText(title);
        t2.setText(description);
        t3.setText(date);
        t4.setText(time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.editItem){
            // isEdited=true;
            // String T=t1.getText().toString();
            //  String D=t2.getText().toString();
            Intent intent1=new Intent(this,EditTaskActivity.class);
            intent1.putExtra(TITLE, title);
            intent1.putExtra(DESCRIPTION, description);
            intent1.putExtra(DATE, date);
            intent1.putExtra(TIME, time);
           // btn.setVisibility(View.VISIBLE);
          //  btn.setEnabled(true);
            startActivityForResult(intent1,EDIT_TASK_CODE);


        }
        return super.onOptionsItemSelected(item);
    }
    public void edited(){
        //   Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        Intent intent3=new Intent(this,MainActivity.class);
        intent3.putExtra(TITLE,title);
        intent3.putExtra(DESCRIPTION,description);
        intent3.putExtra(DATE, date);
        intent3.putExtra(TIME, time);
        setResult(EDIT_TASK_CODE,intent3);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EDIT_TASK_CODE){
            if(resultCode==EditTaskActivity.EDITCODE) {
                title = data.getStringExtra(EditTaskActivity.TASK_TITLE);
                description = data.getStringExtra(EditTaskActivity.TASK_DESCRIPTION);
                date = data.getStringExtra(EditTaskActivity.TASK_DATE);
                time = data.getStringExtra(EditTaskActivity.TASK_TIME);
                t1.setText(title);
                t2.setText(description);
                t3.setText(date);
                t4.setText(time);
                edited();

            }
        }
    }
}
