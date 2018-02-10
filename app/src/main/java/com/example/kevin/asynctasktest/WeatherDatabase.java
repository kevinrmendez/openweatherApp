package com.example.kevin.asynctasktest;

/**
 * Created by Kevin Mendez on 2/9/2018.
 */

public class WeatherDatabase {
    public static final String TABLE_WEATHER = "weather";
    public static final String ID = "_id";
    public static final String COL_CITY = "city";
    public static final String COL_TEMPERATURE = "temperature";

    private static final String CREATE_TABLE_WEATHER = "create table " + TABLE_WEATHER
            + " (" + ID + " integer primary key autoincrement, " + COL_CITY
            + " text not null, " + COL_TEMPERATURE + " text not null);";

    private static final String DB_SCHEMA = CREATE_TABLE_WEATHER;
}
