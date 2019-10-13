package com.example.instalike.db;

import java.util.Date;

public class Post {

    private int id;
    private int user_id;
    private int photo_path;
    private String description;
    private Date date;

    public Post() {}



    public Post(int user_id, int photo_path, String description, Date date) {
        this.user_id = user_id;
        this.photo_path = photo_path;
        this.description=description;
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

    public int getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(int photo_path) {
        this.photo_path = photo_path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
