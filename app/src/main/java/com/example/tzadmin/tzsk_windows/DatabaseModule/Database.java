package com.example.tzadmin.tzsk_windows.DatabaseModule;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.User;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tzadmin on 13.04.17.
 */

public class Database {
    private static SQLiteDatabase db;

    public static void SetUp(SQLiteDatabase dbHelper) {
        db = dbHelper;
    }

    public static void deleteMeas (int meas_id) {

    }

    public static void editMeas (int meas_id) {

    }

    public static void insertMeases (ArrayList<Meas> meases, int user_id) {
        for (Meas meas : meases) {
            ContentValues cv = new ContentValues();
            cv.put("idUser", user_id);
            cv.put("OrderID", meas.OrderID);
            cv.put("OrderNumber", meas.OrderNumber);
            cv.put("Client", meas.Client);
            cv.put("PhoneNumber", meas.PhoneNumber);
            cv.put("Date", meas.Date);
            cv.put("Address", meas.Address);
            cv.put("TimeStart", meas.TimeStart);
            cv.put("EndTime", meas.EndTime);
            cv.put("Comment", meas.Comment);
            db.insert("tbMeas", null, cv);
        }
    }

    public static ArrayList<Meas> selectMeases(int user_id) {

        Cursor cursor = db.query("tbMeas", null, "idUser = " + user_id, null, null, null, null, null);
        ArrayList<Meas> meases = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Meas meas = new Meas();
                meas.id = cursor.getInt(0);
                meas.idUser = cursor.getInt(1);
                meas.OrderID = cursor.getString(2);
                meas.OrderNumber = cursor.getString(3);
                meas.Client = cursor.getString(4);
                meas.PhoneNumber = cursor.getString(5);
                meas.Date = cursor.getString(6);
                meas.Address = cursor.getString(7);
                meas.TimeStart = cursor.getString(8);
                meas.EndTime = cursor.getString(9);
                meas.Comment = cursor.getString(10);
                meases.add(meas);
            } while (cursor.moveToNext());
            return meases;
        }
        else
            return null;
    }

    public static int insertUser (String login, String password) {
        ContentValues cv = new ContentValues();
        Date date = new Date();
        cv.put("login", login);
        cv.put("password", password);
        cv.put("autoLogin", 1);
        db.insert("tbUsers", null, cv);
        User user = lastUserLogin();
        return user.id;
    }

    public static int isUserExist (String login, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM tbUsers WHERE login = ? AND password = ?", new String[] {login, password});
        if(cursor.getCount() == 0)
            return -1;
        else {
            cursor.moveToNext();
            return cursor.getInt(0);
        }
    }

    public static void updateUser (int id, Integer autoLogin) {
        ContentValues cv = new ContentValues();
        cv.put("autoLogin", autoLogin);
        db.update("tbUsers", cv, "id = ?", new String[] { String.valueOf(id) });
    }

    public static void deleteUser (int id) {
        db.delete("tbUsers", "id=" + id, null);
    }

    public static User lastUserLogin () {
        Cursor cursor = db.query("tbUsers", null, "autoLogin=1", null, null, null, null, "1");
        if(cursor.getCount() == 0)
            return null;
        cursor.moveToNext();
        User user = new User();
        user.id = cursor.getInt(0);
        user.login = cursor.getString(1);
        user.password = cursor.getString(2);
        user.autoLogin = cursor.getInt(3);
        return user;
    }
}
