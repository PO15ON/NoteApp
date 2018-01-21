package com.example.ahmed.noteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ahmed on 1/17/2018.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE = "date";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " +  TABLE_NAME  + " (" +
                COLUMN_ID  + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE+ " TEXT UNIQUE NOT NULL, " +
                COLUMN_BODY + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL " + ");";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
