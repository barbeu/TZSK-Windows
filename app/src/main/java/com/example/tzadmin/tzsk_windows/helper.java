package com.example.tzadmin.tzsk_windows;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import java.util.Date;

/**
 * Created by tzadmin on 07.04.17.
 */

public class helper {

    /*enum http params*/
    public static int QUERY = 0;
    public static int LOGIN = 1;
    public static int PASSWORD = 2;
    public static int POST_DATA = 3;

    /*enum http query*/
    public static String QUERY_AUTH = "auth";
    public static String QUERY_GETORDERS = "getorders";

    /*TODO httpServer add config*/
    public static String httpServer = "http://192.168.0.251/tzsk_tst/hs/JavaMobileApp/AnyInquiry/?param=";

    public static void message (Context context, MSG msg, Integer length) {
        String message = null;
        switch (msg) {
            case EMPTY_AUTH_DATA:
                message = "Логин и пароль не может быть пустым.";
                break;
            case INTERNET_NOT_CONNECTING:
                message = "Отсутствует интернет подключение.";
                break;
            case INCORRECT_AUTH_DATA:
                message = "Логин или пароль неверный.";
                break;
        }
        Toast toast = Toast.makeText(context, message, length);
        toast.show();
    }

    /*enum show message*/
    public enum MSG {
        EMPTY_AUTH_DATA,
        INTERNET_NOT_CONNECTING,
        INCORRECT_AUTH_DATA
    }

    public static Integer getTimeMili () {
        Date date = new Date();
        return (int)(long)date.getTime();
    }

    public static boolean InetHasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
