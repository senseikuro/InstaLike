package com.example.instalike.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Database extends SQLiteOpenHelper {

    //Tables de la base
    private static final String USER_TABLE = "User";
    private static final String POST_TABLE = "Post";
    private static final String COMMENT_TABLE = "Comment";
    private static final String LIKE_TABLE = "[Like]";
    private static final String FOLLOW_TABLE = "Follow";



    //Colonnes des tables de la base

    //User
    private static final String USER_COL_ID = "Id";
    private static final String USER_COL_NAME = "Name";
    private static final String USER_COL_SURNAME = "Surname";
    private static final String USER_COL_PASSWORD = "Password";
    private static final String USER_COL_MAIL = "Mail";
    private static final String USER_COL_PSEUDEO = "Pseudeo";
    private static final String USER_COL_DATE = "Date";
    //Post
    private static final String POST_COL_ID = "Id";
    private static final String POST_COL_USER_ID = "User_id";
    private static final String POST_COL_PHOTO_PATH = "Photo_path";
    private static final String POST_COL_DATE = "Date";
    //Comment
    private static final String COMMENT_COL_ID = "Id";
    private static final String COMMENT_COL_USER_ID = "User_id";
    private static final String COMMENT_COL_POST_ID = "Post_id";
    private static final String COMMENT_COL_CONTENT = "Content";
    private static final String COMMENT_COL_DATE = "Date";
    //Like
    private static final String LIKE_COL_ID = "Id";
    private static final String LIKE_COL_USER_ID = "User_id";
    private static final String LIKE_COL_POST_ID = "Post_id";
    private static final String LIKE_COL_DATE = "Date";
    //Follow
    private static final String FOLLOW_COL_ID = "Id";
    private static final String FOLLOW_COL_USER_ID = "User_id";
    private static final String FOLLOW_COL_USER_ID_FOLLOWED = "User_id_followed";
    private static final String FOLLOW_COL_DATE = "Date";




    private static final String CREATE_USER_TABLE =

            "CREATE TABLE " + USER_TABLE + " ("
            + USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COL_NAME + " TEXT NOT NULL, "
            + USER_COL_SURNAME + " TEXT NOT NULL, "
            + USER_COL_MAIL + " TEXT NOT NULL, "
            + USER_COL_PASSWORD + " TEXT NOT NULL, "
            + USER_COL_PSEUDEO + " TEXT NOT NULL, "
            + USER_COL_DATE + " DATETIME NOT NULL);";


    private static final String CREATE_POST_TABLE =

            "CREATE TABLE " + POST_TABLE + " ("
             + POST_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + POST_COL_USER_ID + " INT NOT NULL, "
             + POST_COL_PHOTO_PATH + " TEXT NOT NULL, "
             + POST_COL_DATE + " DATETIME NOT NULL,"
             + "FOREIGN KEY (" + POST_COL_USER_ID + ") REFERENCES " + USER_TABLE + "(Id));";


    private static final String CREATE_COMMENT_TABLE =

            "CREATE TABLE " + COMMENT_TABLE + " ("
            + COMMENT_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COMMENT_COL_USER_ID + " INT NOT NULL, "
            + COMMENT_COL_POST_ID + " INT NOT NULL, "
            + COMMENT_COL_CONTENT + " TEXT NOT NULL,"
            + COMMENT_COL_DATE + " DATETIME NOT NULL,"
            + "FOREIGN KEY (" + COMMENT_COL_USER_ID + ") REFERENCES " + USER_TABLE + "(Id),"
            + "FOREIGN KEY (" + COMMENT_COL_POST_ID + ") REFERENCES " + POST_TABLE + "(Id));";


    private static final String CREATE_LIKE_TABLE =

            "CREATE TABLE " + LIKE_TABLE + " ("
            + LIKE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LIKE_COL_USER_ID + " INT NOT NULL, "
            + LIKE_COL_POST_ID + " INT NOT NULL, "
            + LIKE_COL_DATE + " DATETIME NOT NULL,"
            + "FOREIGN KEY (" + LIKE_COL_USER_ID + ") REFERENCES " + USER_TABLE + "(Id),"
            + "FOREIGN KEY (" + LIKE_COL_POST_ID + ") REFERENCES " + POST_TABLE + "(Id));";


    private static final String CREATE_FOLLOW_TABLE =

            "CREATE TABLE " + FOLLOW_TABLE + " ("
            + FOLLOW_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOLLOW_COL_USER_ID + " INT NOT NULL, "
            + FOLLOW_COL_USER_ID_FOLLOWED + " INT NOT NULL, "
            + FOLLOW_COL_DATE + " DATETIME NOT NULL,"
            + "FOREIGN KEY (" + FOLLOW_COL_USER_ID + ") REFERENCES " + USER_TABLE + "(Id),"
            + "FOREIGN KEY (" + FOLLOW_COL_USER_ID_FOLLOWED + ") REFERENCES " + USER_TABLE + "(Id));";





    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //On créé successivement les tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_POST_TABLE);
        db.execSQL(CREATE_COMMENT_TABLE);
        db.execSQL(CREATE_LIKE_TABLE);
        db.execSQL(CREATE_FOLLOW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + USER_TABLE + ";");
        onCreate(db);
    }
}
