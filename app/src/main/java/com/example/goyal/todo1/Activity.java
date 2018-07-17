package com.example.goyal.todo1;

import java.util.Calendar;

public class Activity {
    private String todo;
    private  long id;
    private String date;
    private String time;
    private long timeInEpochs;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public long getTimeInEpochs() {
        return timeInEpochs;
    }

    public void setTimeInEpochs(long timeInEpochs) {
        this.timeInEpochs = timeInEpochs;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInEpochs);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        ++month;

        this.date = day + "/" + month + "/" + year;

        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        this.time = hour + ":" + min;

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Activity(String todo,String date,String time,String description) {
        this.todo = todo;
        this.date=date;
        this.time = time;
        this.description = description;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
