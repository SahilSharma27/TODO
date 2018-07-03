package com.example.android.todo;

/**
 * Created by Sahil Sharma on 26-06-2018.
 */

public class Tasks {
private String title;
private String description;

    public Tasks(String title,String description) {
        this.title = title;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
