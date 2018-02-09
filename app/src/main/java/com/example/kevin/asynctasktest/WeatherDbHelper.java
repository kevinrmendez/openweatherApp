package com.example.kevin.asynctasktest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevin on 2/8/2018.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    public WeatherDbHelper(Context context) {
        super(context, "weather.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table weather (id text,city text, temperature text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists weather");
        onCreate(db);

    }
}
