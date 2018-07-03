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
    Intent intent;
    String title,description;
    Button btn ;
    public static final String TITLE="title";
    public static final String DESCRIPTION="description";
    public static final int EDIT_TASK_CODE=1;
    public boolean isEdited=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        t1=findViewById(R.id.Title);
        t2=findViewById(R.id.Description);
        btn=findViewById(R.id.back);
        btn.setEnabled(false);
        intent=getIntent();
         title=intent.getStringExtra(MainActivity.CLICKED_TASK_TITLE);
         description=intent.getStringExtra(MainActivity.CLICKED_TASK_DESCRIPTION);
        t1.setText(title);
        t2.setText(description);
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
            isEdited=true;
            String T=t1.getText().toString();
            String D=t2.getText().toString();
            Intent intent1=new Intent(this,EditTaskActivity.class);
            intent1.putExtra(TITLE,T);
            intent1.putExtra(DESCRIPTION,D);
            btn.setEnabled(true);
            startActivityForResult(intent1,EDIT_TASK_CODE);


        }
        return super.onOptionsItemSelected(item);
    }
    public void edited(View view){
        Intent intent3=new Intent(this,MainActivity.class);
        intent3.putExtra(TITLE,title);
        intent3.putExtra(DESCRIPTION,description);
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
                t1.setText(title);
                t2.setText(description);
            }
        }
    }
}
