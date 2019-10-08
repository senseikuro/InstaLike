package com.example.instalike.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_USER = "table_user";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_SURNAME = "Surname";
    private static final String COL_MAIL = "Mail";
    private static final String COL_PSEUDEO = "Pseudeo";
    private static final String COL_DATE = "Date";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_USER + " ("

            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_SURNAME + " TEXT NOT NULL, "
            + COL_MAIL + " TEXT NOT NULL, "
            + COL_PSEUDEO + " TEXT NOT NULL, "
            + COL_DATE + " DATETIME NOT NULL);";

    public Database(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        onCreate(db);
    }
}
