package com.matthelium.birthdays.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.matthelium.birthdays.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }

    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }

    public void closeDb() {
        if (myDbHelper != null) {
            myDbHelper.close();
        }
    }
    public void insertToDb(String name, Calendar c, String presents){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.NAME, name);
        cv.put(MyConstants.YEAR, c.get(Calendar.YEAR));
        cv.put(MyConstants.MONTH, c.get(Calendar.MONTH)+1);
        cv.put(MyConstants.DAY, c.get(Calendar.DAY_OF_MONTH));
        cv.put(MyConstants.PRESENTS, presents);
        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public List<User> getFromDb(){
        List<User> tempList = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null, null, null, "CASE " +
                "WHEN LENGTH(" + MyConstants.MONTH + ") = 1 THEN '0' || " + MyConstants.MONTH + " " +
                "ELSE " + MyConstants.MONTH + " " +
                "END, " +
                "CASE " +
                "WHEN LENGTH(" + MyConstants.DAY + ") = 1 THEN '0' || " + MyConstants.DAY + " " +
                "ELSE " + MyConstants.DAY + " " +
                "END ASC");
        while (cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyConstants.NAME));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex(MyConstants.DAY));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex(MyConstants.MONTH));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex(MyConstants.YEAR));
            @SuppressLint("Range") String presents = cursor.getString(cursor.getColumnIndex(MyConstants.PRESENTS));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            User user = new User(name, calendar.getTime(), presents, id);

            tempList.add(user);
        }

        cursor.close();
        return tempList;
    }

    public List<User> getUsersWithBirthday(String todayDate) {
        List<User> usersWithBirthday = new ArrayList<>();
        String[] columns = {MyConstants.NAME, MyConstants.YEAR, MyConstants.MONTH, MyConstants.DAY, MyConstants.PRESENTS, MyConstants._ID};

        // Выбираем пользователей, у которых сегодня ДР
        Cursor cursor = db.query(
                MyConstants.TABLE_NAME,
                columns,
                MyConstants.MONTH + " = ? AND " + MyConstants.DAY + " = ?",
                new String[]{todayDate.split("-")[0], todayDate.split("-")[1]},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyConstants.NAME));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex(MyConstants.YEAR));
            @SuppressLint("Range") int month = cursor.getInt(cursor.getColumnIndex(MyConstants.MONTH));
            @SuppressLint("Range") int day = cursor.getInt(cursor.getColumnIndex(MyConstants.DAY));
            @SuppressLint("Range") String presents = cursor.getString(cursor.getColumnIndex(MyConstants.PRESENTS));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            User user = new User(name, calendar.getTime(), presents, id);
            usersWithBirthday.add(user);
        }

        cursor.close();

        return usersWithBirthday;
    }

    public void deleteRow(int data){
        db.delete(MyConstants.TABLE_NAME, MyConstants._ID + " = ?", new String[]{String.valueOf(data)} );
    }
}
