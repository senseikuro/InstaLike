package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class NotifyActions {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String NOTIFY_TABLE = "Notification";

    //Columns names
    //USER
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_USER_ID = "User_id";
    private static final int NUM_COL_USER_ID = 1;

    private static final String COL_USER_NOTIFY_ID = "User_notify_id";
    private static final int NUM_COL_USER_NOTIFY_ID = 2;

    private static final String COL_ACTION="Event";
    private static final int NUM_COL_Action=3;

    private static final String COL_POST_ID="Post_id";
    private static final int NUM_POST_ID=4;

    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 5;
    private SQLiteDatabase bdd;

    private Database Database;

    public NotifyActions(Context context){
        //On crée la BDD et sa table
        Database = new Database(context, NOM_BDD, null, VERSION_BDD);
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertNotification(Notify notification){
        bdd = Database.getWritableDatabase();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USER_ID, notification.getUser_id());
        values.put(COL_USER_NOTIFY_ID, notification.getUser_notified_id());
        values.put(COL_ACTION, notification.getEvent());
        values.put(COL_POST_ID,notification.getPost_id());
        values.put(COL_DATE,notification.getDate().toString());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(NOTIFY_TABLE, null, values);
    }
    public int removeNotificationById(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(NOTIFY_TABLE, COL_ID + " = " +id, null);
    }

    public ArrayList<Integer> getAllUserNotifier(int userID){
        ArrayList<Integer> users=new ArrayList<Integer>();
        bdd= Database.getReadableDatabase();
        String req="select * from Notification where User_notify_id="+userID;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            users.add(curseur.getInt(1)) ;
            curseur.moveToNext();
        }
        curseur.close();
        return users;
    }

    public ArrayList<Notify> getAllNotificationOfUser(int user_notified){
        bdd= Database.getReadableDatabase();
        String req="select * from Notification where User_notify_id="+user_notified;
        Cursor curseur=bdd.rawQuery(req,null);
        ArrayList<Notify> listNotification= new ArrayList<Notify>();
        Notify notification= new Notify();
        curseur.moveToFirst();
        Date now = new Date (Calendar.getInstance().getTime().getTime());
        int i=0;
        while (!curseur.isAfterLast()){

            int userID=curseur.getInt(1);
            int userNotified=curseur.getInt(2);
            String event=curseur.getString(3);
            int postId=curseur.getInt(4);
            listNotification.add(new Notify(userID,userNotified,postId,event,now));
            listNotification.get(i).setId(curseur.getInt(0));
            i++;
            curseur.moveToNext();
        }
        return listNotification;
    }
    public String getEvent(int idNotification){
        bdd= Database.getReadableDatabase();
        String req="select * from Notification where Id="+idNotification;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        String event="";
        if (!curseur.isAfterLast()){
            event=curseur.getString(3);
        }
        return event;
    }
    public ArrayList<String> getNotification(int user_notified, ArrayList<String> nameNotifier){
        bdd= Database.getReadableDatabase();
        String req="select * from Notification where User_notify_id="+user_notified;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        ArrayList<String> notifactions= new ArrayList<String>();
        int i=0;
        while(!curseur.isAfterLast()){
            switch(curseur.getString(3)){
                case "follow":
                    notifactions.add(nameNotifier.get(i)+" vient de vous suivre!");
                    break;
                case "like":
                    notifactions.add(nameNotifier.get(i)+" vient de liker un de vos posts");
                    break;
                case "comment":
                    notifactions.add(nameNotifier.get(i)+" vient de commenter un de vos posts");
                    break;
                default:
                    i++;

            }
            curseur.moveToNext();
        }
        curseur.close();
        close();
        return notifactions;
    }
}
