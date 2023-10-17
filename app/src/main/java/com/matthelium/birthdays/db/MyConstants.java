package com.matthelium.birthdays.db;

public class MyConstants {
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String PRESENTS = "presents";
    public static final String DB_NAME = "Birthdays.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," +
            YEAR + " INTEGER," + MONTH + " INTEGER," + DAY + " INTEGER," + PRESENTS + " TEXT);";
    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
}
