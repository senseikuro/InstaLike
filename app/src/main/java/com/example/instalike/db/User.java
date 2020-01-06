package com.example.instalike.db;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String pseudeo;
    private byte[] photo_pp;
    private String description;
    private String date;

    public User() {}



    public User(String name, String surname, String mail,String password ,String pseudeo, byte[] photo_pp, String description, String date) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.pseudeo = pseudeo;
        this.photo_pp=photo_pp;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudeo() {
        return pseudeo;
    }

    public void setPseudeo(String pseudeo) {
        this.pseudeo = pseudeo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getPhoto_path() {
        return photo_pp;
    }

    public void setPhoto_path(byte[] photo_path) {
        this.photo_pp = photo_path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
