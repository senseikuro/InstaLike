package com.example.instalike.db;

import java.util.Date;

public class Like {

    private int id;
    private int user_id;
    private int post_id;
    private Date date;

    public Like() {}



    public Like(int user_id, int post_id, Date date) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.date = date;
    }


    //GETTERS AND SETTERS
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
