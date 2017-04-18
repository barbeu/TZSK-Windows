package com.example.tzadmin.tzsk_windows.DatabaseModule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tzadmin on 13.04.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbUsers ("
                + "id integer primary key autoincrement,"
                + "login text,"
                + "password text,"
                + "autoLogin integer" + ");");

        db.execSQL("create table tbMeas ("
                + "id integer primary key autoincrement,"
                + "idUser integer,"
                + "OrderID text,"
                + "OrderNumber text,"
                + "Client text,"
                + "PhoneNumber text,"
                + "Date text,"
                + "Address text,"
                + "TimeStart text,"
                + "EndTime text,"
                + "Comment text,"
                + "FOREIGN KEY (idUser) REFERENCES tbUsers(id)" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

