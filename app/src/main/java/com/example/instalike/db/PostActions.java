package com.example.instalike.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

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

    private static final String COL_DESCRIPTION="Description";
    private static final int NUM_COL_DESCRIPTION=4;



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
        values.put(COL_DESCRIPTION,post.getDescription());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(POST_TABLE, null, values);
    }

    public int updatePost(int id, Post post){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(COL_USER_ID, post.getUser_id());
        values.put(COL_PHOTO_PATH, post.getPhoto_path());
        values.put(COL_DESCRIPTION,post.getDescription());
        //values.put(COL_DATE, user.getDate().toString());

        return bdd.update(POST_TABLE, values, COL_ID + " = " +id, null);
    }

    public int removePostWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(POST_TABLE, COL_ID + " = " +id, null);
    }

    public ArrayList<Post> getAllPost(int user){
        ArrayList<Post> posts=new ArrayList<Post>();
        bdd= Database.getReadableDatabase();
        String req="select * from Post where User_id="+user;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        int i=0;
        while(!curseur.isAfterLast()){
            int id=curseur.getInt(0);
            int userId=curseur.getInt(1);
            int photo=curseur.getInt(2);
            String description=curseur.getString(4);
            Date today = Calendar.getInstance().getTime();
            posts.add(new Post(userId,photo,description,today));
            posts.get(i).setId(id);
            i++;
            curseur.moveToNext();
        }
        curseur.close();
        return posts;
    }

    public int getNbComment(int postID){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from Comment where Post_id="+postID;
        Cursor mCount=bdd.rawQuery(req,null);
        mCount.moveToFirst();
        int nbComment=mCount.getInt(0);
        mCount.close();
        return nbComment;
    }
    public int getnbPost(int userID){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from Post where User_id="+userID;
        Cursor mCount=bdd.rawQuery(req,null);
        mCount.moveToFirst();
        int nbPost=mCount.getInt(0);
        mCount.close();
        return nbPost;
    }
    public int getNbLike(int postID){
        bdd= Database.getReadableDatabase();
        String req="select COUNT(*) from [Like] where Post_id="+postID;
        Cursor mCount=bdd.rawQuery(req,null);
        mCount.moveToFirst();
        int nbLikes=mCount.getInt(0);
        mCount.close();
        return nbLikes;
    }

    public Post getPostFromID(int postID){
        Post post= new Post();
        bdd= Database.getReadableDatabase();
        String req="select * from Post where Id="+postID;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        if(!curseur.isAfterLast()){
            int userId=curseur.getInt(1);
            int photo=curseur.getInt(2);
            String description=curseur.getString(4);
            Date today = Calendar.getInstance().getTime();
            post.setId(postID);
            post.setUser_id(userId);
            post.setDescription(description);
            post.setPhoto_path(photo);
        }
        curseur.close();
        return post;
    }

    public String getUserName(int userID){
        bdd= Database.getReadableDatabase();
        String req="select * from User where Id="+userID;
        Cursor curseur=bdd.rawQuery(req,null);
        curseur.moveToFirst();
        String pseudo="";
        if(!curseur.isAfterLast()){
            pseudo=curseur.getString(5);
        }
        curseur.close();
        return pseudo;
    }

    public Boolean isAbonnement(ArrayList<Integer> users, int userToTest){
        boolean isabonne=false;

        for (int i=0;i<users.size();i++){
            if (users.get(i)==userToTest){
                return true;
            }
        }
        return false;
    }
    public ArrayList<Post> getActuality(ArrayList<Integer> users){
        bdd= Database.getReadableDatabase();
        String req= "select * from Post order by Date";
        Cursor curseur = bdd.rawQuery(req, null);
        curseur.moveToFirst();
        ArrayList<Post> actualityPost=new ArrayList<Post>();
        while(!curseur.isAfterLast()){
            if (isAbonnement(users,curseur.getInt(1))){
                actualityPost.add(getPostFromID(curseur.getInt(0)));
            }
            curseur.moveToNext();
        }
        return actualityPost;
    }
    public int getUserIDFromPost(int postId){
        bdd= Database.getReadableDatabase();
        String req= "select * from Post where Id="+postId;
        Cursor curseur = bdd.rawQuery(req, null);
        curseur.moveToFirst();
        int ownerPost=-1;
        if(!curseur.isAfterLast()){
            ownerPost=curseur.getInt(1);

        }
        return ownerPost;
    }
}
