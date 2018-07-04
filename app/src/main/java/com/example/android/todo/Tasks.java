package com.example.android.todo;

/**
 * Created by Sahil Sharma on 26-06-2018.
 */

public class Tasks {
private String title;
private String description;
private long id;



    public Tasks(String title, String description) {
        this.title = title;
        this.description=description;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
