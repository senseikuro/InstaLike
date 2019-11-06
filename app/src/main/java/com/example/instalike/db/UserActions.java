package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserActions {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String USER_TABLE = "User";

    //Columns names
    //USER
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_NAME = "Name";
    private static final int NUM_COL_NAME = 1;

    private static final String COL_SURNAME = "Surname";
    private static final int NUM_COL_SURNAME = 2;

    private static final String COL_PASSWORD = "Password";
    private static final int NUM_COL_PASSWORD = 3;

    private static final String COL_MAIL = "Mail";
    private static final int NUM_COL_MAIL = 4;

    private static final String COL_PSEUDEO = "Pseudeo";
    private static final int NUM_COL_PSEUDEO = 5;


    private static final String COL_PHOTO = "Photo";
    private static final int NUM_COL_PHOTO = 6;

    private static final String COL_DESCRIPTION = "Description";
    private static final int NUM_COL_DESCRIPTION = 7;


    private static final String COL_DATE= "Date";
    private static final int NUM_COL_DATE = 8;



    private SQLiteDatabase bdd;

    private Database Database;

    public UserActions(Context context){
        //On crée la BDD et sa table
        Database = new Database(context, NOM_BDD, null, VERSION_BDD);

    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = Database.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertUser(User user){

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, user.getName());
        values.put(COL_SURNAME, user.getSurname());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_MAIL, user.getMail());
        values.put(COL_PSEUDEO, user.getPseudeo());
        values.put(COL_PHOTO,user.getPhoto_path());
        values.put(COL_DESCRIPTION,user.getDescription());
        values.put(COL_DATE, user.getDate().toString());


        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(USER_TABLE, null, values);
    }

    public int updateUser(int id, User user){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_NAME, user.getName());
        values.put(COL_SURNAME, user.getSurname());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_MAIL, user.getMail());
        values.put(COL_PSEUDEO, user.getPseudeo());
        values.put(COL_PHOTO,user.getPhoto_path());
        values.put(COL_DESCRIPTION,user.getDescription());
        values.put(COL_DATE, user.getDate().toString());

        return bdd.update(USER_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removeUserWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(USER_TABLE, COL_ID + " = " +id, null);
    }

    public User getUserWithPseudeo(String pseudeo){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(USER_TABLE, new String[] {COL_ID, COL_NAME,COL_SURNAME,COL_PASSWORD, COL_MAIL, COL_PSEUDEO}, COL_PSEUDEO + " LIKE \"" + pseudeo +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private User cursorToUser(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        User user = new User();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        user.setId(c.getInt(NUM_COL_ID));
        user.setName(c.getString(NUM_COL_NAME));
        user.setSurname(c.getString(NUM_COL_SURNAME));
        user.setMail(c.getString(NUM_COL_MAIL));
        user.setPassword(c.getString(NUM_COL_PASSWORD));
        user.setPseudeo(c.getString(NUM_COL_PSEUDEO));
        //Et avec Date ?



        //On ferme le cursor
        c.close();

        //On retourne le user
        return user;
    }

    public byte[] getUserPP(int id_user){
        bdd=Database.getReadableDatabase();
        String req="select * from User where Id="+id_user;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        byte[] pp=null;
        if (!curseur.isAfterLast()){
            pp=curseur.getBlob(6);
        }
        return pp;
    }

    public String getUserDescription(int id_user) {
        bdd = Database.getReadableDatabase();
        String req = "select * from User where Id=" + id_user;
        Cursor curseur = bdd.rawQuery(req, null);
        curseur.moveToFirst();
        String description="";
        if (!curseur.isAfterLast()) {
            description = curseur.getString(7);
        }
        return description;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users=new ArrayList<User>();
        bdd= Database.getReadableDatabase();
        String req="select * from User";
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        int i=0;
        while(!curseur.isAfterLast()){
            users.add(new User());
            users.get(i).setId(curseur.getInt(0));
            users.get(i).setName(curseur.getString(1));
            users.get(i).setSurname(curseur.getString(2));
            users.get(i).setPseudeo(curseur.getString(5));
            users.get(i).setPhoto_path(curseur.getBlob(6));
            users.get(i).setDescription(curseur.getString(7));
            i++;
            curseur.moveToNext();
        }
        curseur.close();
        return users;
    }
    public User getUserWithID(int id){
        bdd=Database.getReadableDatabase();
        String req="select * from User where Id="+id;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        User user= new User();
        if(!curseur.isAfterLast()){
            user.setId(curseur.getInt(0));
            user.setName(curseur.getString(1));
            user.setSurname(curseur.getString(2));
            user.setMail(curseur.getString(3));
            user.setPassword(curseur.getString(4));
            user.setPseudeo(curseur.getString(5));
        }
        return user;
    }
}
