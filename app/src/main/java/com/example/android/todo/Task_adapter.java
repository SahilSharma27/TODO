package com.example.android.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sahil Sharma on 26-06-2018.
 */

public class Task_adapter extends ArrayAdapter {
    ArrayList<Tasks> tasks;
    LayoutInflater inflater;
    public Task_adapter(Context context,ArrayList<Tasks>tasks){
        super(context,0,tasks);
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tasks=tasks;
    }
    @Override
    public int getCount() {
       return tasks.size();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output =inflater.inflate(R.layout.task_row_layout,parent,false);
        TextView nameTextView=output.findViewById(R.id.taskTitle);
        Tasks task=tasks.get(position);
        nameTextView.setText(task.getTitle());
        return output;
    }
}
