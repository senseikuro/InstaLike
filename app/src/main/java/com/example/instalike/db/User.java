package com.example.instalike.db;

import java.util.Date;

public class User {

    private int id;
    private String name;
    private String surname;
    private String mail;
    private String pseudeo;
    private Date date;

    public User() {}



    public User(String name, String surname, String mail, String pseudeo) {
        this.name = name;
        this.surname = surname;
        this.pseudeo = pseudeo;
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

    public String getPseudeo() {
        return pseudeo;
    }

    public void setPseudeo(String pseudeo) {
        this.pseudeo = pseudeo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
