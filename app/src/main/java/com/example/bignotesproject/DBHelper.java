package com.example.bignotesproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "notesDB";

    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_TASKS = "tasksDB";
    public static final String TABLE_PASSWORD = "passwordDB";

    public static final String KEY_ID = "_id";
    public static final String KEY_TEXT = "text";
    public static final String KEY_CHECKER = "checker";
    public static final String KEY_PIC = "pic";
    public static final String KEY_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT + " TEXT," + KEY_PIC + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + TABLE_TASKS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT + " TEXT," + KEY_CHECKER + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + TABLE_PASSWORD + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
        onCreate(db);
    }
}
