package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PostActions {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String POST_TABLE = "Post";

    //Columns names
    //USER
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_USER_ID = "User_id";
    private static final int NUM_COL_USER_ID = 1;

    private static final String COL_PHOTO_PATH = "Photo_path";
    private static final int NUM_COL_PHOTO_PATH = 2;

    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 3;



    private SQLiteDatabase bdd;

    private Database Database;

    public PostActions(Context context){
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

    public long insertPost(Post post){

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USER_ID, post.getUser_id());
        values.put(COL_PHOTO_PATH, post.getPhoto_path());
        values.put(COL_DATE, post.getDate().toString());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(POST_TABLE, null, values);
    }

    public int updatePost(int id, Post post){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, post.getUser_id());
        values.put(COL_PHOTO_PATH, post.getPhoto_path());

        //values.put(COL_DATE, user.getDate().toString());

        return bdd.update(POST_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removePostWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(POST_TABLE, COL_ID + " = " +id, null);
    }

}
