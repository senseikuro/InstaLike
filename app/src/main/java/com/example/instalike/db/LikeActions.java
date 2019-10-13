package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LikeActions {


    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String LIKE_TABLE = "Like";

    //Columns names
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_USER_ID = "User_id";
    private static final int NUM_COL_USER_ID = 1;

    private static final String COL_POST_ID = "Post_id";
    private static final int NUM_COL_POST_ID = 2;

    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 3;



    private SQLiteDatabase bdd;

    private Database Database;

    public LikeActions(Context context){
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

    public long insertLike(Like like){
        bdd = Database.getWritableDatabase();

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USER_ID, like.getUser_id());
        values.put(COL_POST_ID, like.getPost_id());
        values.put(COL_DATE, like.getDate().toString());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(LIKE_TABLE, null, values);
    }

    public int updateLike(int id, Like like){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, like.getUser_id());
        values.put(COL_POST_ID, like.getPost_id());
        values.put(COL_DATE, like.getDate().toString());

        return bdd.update(LIKE_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removeLikeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(LIKE_TABLE, COL_ID + " = " +id, null);
    }

    public boolean postIsLike(int userId, int postId){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from [Like] where User_id="+userId+" and Post_id="+postId;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToLast();
        boolean postISLike=false;
        if(curseur.getInt(0)!=0){
            postISLike=true;
            System.out.println(curseur.getInt(0));
        }
        curseur.close();
        return postISLike;
    }
    public int getPostLike(int userId, int postId){
        bdd= Database.getReadableDatabase();
        String req="select * from [Like] where User_id="+userId+" and Post_id="+postId;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        Like postLike= new Like();
        int likeID=-1;
        if(!curseur.isAfterLast()){
            likeID=curseur.getInt(0);
        }
        curseur.close();
        return likeID;
    }
}
