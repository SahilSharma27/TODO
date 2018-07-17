package com.example.android.todo;

/**
 * Created by Sahil Sharma on 26-06-2018.
 */

public class Tasks {
private String title;
private String description;
private long id;
    private String date;
    private String time;



    public Tasks(String title, String description) {
        this.title = title;
        this.description=description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
