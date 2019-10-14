package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FollowActions {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "instalike.db";

    //Tables names
    private static final String FOLLOW_TABLE = "Follow";

    //Columns names
    //USER
    private static final String COL_ID = "Id";
    private static final int NUM_COL_ID = 0;

    private static final String COL_USER_ID = "User_id";
    private static final int NUM_COL_USER_ID = 1;

    private static final String COL_USER_ID_FOLLOWED = "User_id_followed";
    private static final int NUM_COL_USER_ID_FOLLOWED = 2;

    private static final String COL_DATE = "Date";
    private static final int NUM_COL_DATE = 3;



    private SQLiteDatabase bdd;

    private Database Database;

    public FollowActions(Context context){
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

    public long insertFollow(Follow follow){
        bdd = Database.getWritableDatabase();

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USER_ID, follow.getUser_id());
        values.put(COL_USER_ID_FOLLOWED, follow.getUser_id_followed());
        values.put(COL_DATE, follow.getDate().toString());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(FOLLOW_TABLE, null, values);
    }

    public int updateFollow(int id, Follow follow){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, follow.getUser_id());
        values.put(COL_USER_ID_FOLLOWED, follow.getUser_id_followed());
        values.put(COL_DATE, follow.getDate().toString());

        return bdd.update(FOLLOW_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removeFollowWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(FOLLOW_TABLE, COL_ID + " = " +id, null);
    }

    public boolean isFollowed(int current_User, int follow_user)
    {
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from Follow where User_id="+current_User+" and User_id_followed="+follow_user;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToLast();
        boolean profilIsFollow=false;
        if(curseur.getInt(0)!=0){
            profilIsFollow=true;
        }
        curseur.close();
        return profilIsFollow;
    }
    public int getFollow(int current_User, int follow_user)
    {
        bdd= Database.getReadableDatabase();
        String req="select * from Follow where User_id="+current_User+" and User_id_followed="+follow_user;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToLast();
        int follow=-1;
        if(curseur.getInt(0)!=0){
            follow=curseur.getInt(0);
        }
        curseur.close();
        return follow;
    }
    public int getNbFollow(int user_post){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from Follow where User_id_followed="+user_post;
        Cursor mCount=bdd.rawQuery(req,null);
        mCount.moveToFirst();
        int nbFollow=mCount.getInt(0);
        mCount.close();
        return nbFollow;
    }
    public int getNbAbonnement(int user_post){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from Follow where User_id="+user_post;
        Cursor mCount=bdd.rawQuery(req,null);
        mCount.moveToFirst();
        int nbFollow=mCount.getInt(0);
        mCount.close();
        return nbFollow;
    }

    public ArrayList<Integer> getAllAbonnement(int user_ID){
        bdd=Database.getReadableDatabase();
        String req="select * from Follow where User_id="+user_ID+" order by Date";
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        ArrayList<Integer> abonnement= new ArrayList<Integer>();
        while (!curseur.isAfterLast()){
            abonnement.add(curseur.getInt(2));
            curseur.moveToNext();
        }
        curseur.close();
        return abonnement;
    }
    public ArrayList<Integer> getAllAbonne(int user_ID){
        bdd=Database.getReadableDatabase();
        String req="select * from Follow where User_id_followed="+user_ID+" order by Date";
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        ArrayList<Integer> abonnement= new ArrayList<Integer>();
        while (!curseur.isAfterLast()){
            abonnement.add(curseur.getInt(1));
            curseur.moveToNext();
        }
        curseur.close();
        return abonnement;
    }
}
