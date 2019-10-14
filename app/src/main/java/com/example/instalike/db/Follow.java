package com.example.instalike.db;

import java.util.Date;

public class Follow {

    private int id;
    private int user_id;
    private int user_id_followed;
    private Date date;

    public Follow() {}



    public Follow(int user_id, int user_id_followed_id, Date date) {
        this.user_id = user_id;
        this.user_id_followed = user_id_followed_id;
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

    public int getUser_id_followed() {
        return user_id_followed;
    }

    public void setUser_id_followed(int user_id_followed) {
        this.user_id_followed = user_id_followed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
