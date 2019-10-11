package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CommentActions {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String COMMENT_TABLE = "Comment";

    //Columns names
    //USER
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_USER_ID = "User_id";
    private static final int NUM_COL_USER_ID = 1;

    private static final String COL_POST_ID = "Post_id";
    private static final int NUM_COL_POST_ID = 2;

    private static final String COL_CONTENT = "Content";
    private static final int NUM_COL_CONTENT = 3;

    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 4;



    private SQLiteDatabase bdd;

    private Database Database;

    public CommentActions(Context context){
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

    public long insertComment(Comment comment){

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USER_ID, comment.getUser_id());
        values.put(COL_POST_ID, comment.getPost_id());
        values.put(COL_CONTENT, comment.getContent());
        values.put(COL_DATE, comment.getDate().toString());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(COMMENT_TABLE, null, values);
    }

    public int updateComment(int id, Comment comment){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, comment.getUser_id());
        values.put(COL_POST_ID, comment.getPost_id());
        values.put(COL_CONTENT, comment.getContent());
        values.put(COL_DATE, comment.getDate().toString());

        return bdd.update(COMMENT_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removeCommentWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(COMMENT_TABLE, COL_ID + " = " +id, null);
    }

}
