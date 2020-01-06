package com.example.instalike.db;

import java.util.Date;

public class Notify {
    private int id;
    private int user_id;
    private int user_notified_id;
    private int post_id;
    private String event;
    private String date;

    public Notify() {}



    public Notify(int user_id, int user_notified_id, int post_id, String event, String date) {
        this.user_id = user_id;
        this.user_notified_id = user_notified_id;
        this.post_id=post_id;
        this.event=event;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_notified_id() {
        return user_notified_id;
    }

    public void setUser_notified_id(int user_notified_id) {
        this.user_notified_id = user_notified_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //GETTERS AND SETTERS

}

