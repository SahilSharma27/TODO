package com.example.android.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {
    EditText ed1;
    EditText ed2;
    Button btn;
    Intent intent;
    public static String Title;
    public static String Description;
    public static final String TASK_TITLE="title";
    public static final String TASK_DESCRIPTION="description";
    public static final int EDITCODE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ed1=findViewById(R.id.editText);
        ed2=findViewById(R.id.editText2);
        btn=findViewById(R.id.btn);

         intent=getIntent();

        Title= intent.getStringExtra(DescriptionActivity.TITLE);
        Description = intent.getStringExtra(DescriptionActivity.DESCRIPTION);
        ed1.setText(Title);
        ed2.setText(Description);

    }

   public void editDetails(View view){
       Title= ed1.getText().toString();
       Description = ed2.getText().toString();
       intent.putExtra(TASK_TITLE,Title);
       intent.putExtra(TASK_DESCRIPTION,Description);
       setResult(EDITCODE,intent);
       finish();
   }
}
