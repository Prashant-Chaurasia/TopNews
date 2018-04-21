package com.example.prashant_admin.fetchnews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prashant_admin.fetchnews.model.News;

import static com.example.prashant_admin.fetchnews.database.NewsContract.*;

public class NewsDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "news.db";
    public static final int DATABASE_VERSION = 1;

    public NewsDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " +
                NewsEntry.TABLE_NAME + "(" +
                NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NewsEntry.COLUMN_ID + " TEXT, " +
                NewsEntry.COLUMN_NAME + " TEXT, " +
                NewsEntry.COLUMN_AUTHOR + " TEXT, " +
                NewsEntry.COLUMN_TITLE + " TEXT, " +
                NewsEntry.COLUMN_DESCRIPTION + " TEXT, " +
                NewsEntry.COLUMN_URL + " TEXT, " +
                NewsEntry.COLUMN_URL_TO_IMAGE + " TEXT, " +
                NewsEntry.COLUMN_PUBLISHED_AT +" TEXT," +
                "saveTime" + "  TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                ");";

        db.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NewsEntry.TABLE_NAME);
        onCreate(db);
    }
}
