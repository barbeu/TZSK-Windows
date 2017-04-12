package com.example.tzadmin.tzsk_windows;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tzadmin on 07.04.17.
 */

public class helper {
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

    enum MSG {
        EMPTY_AUTH_DATA,
        INTERNET_NOT_CONNECTING,
        INCORRECT_AUTH_DATA
    }

}
