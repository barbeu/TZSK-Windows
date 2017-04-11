package com.example.tzadmin.tzsk_windows;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tzadmin on 07.04.17.
 */

public class helper {
    public static String httpServer = "http://192.168.0.251/tzsk_tst/hs/JavaMobileApp/AnyInquiry/?param=";

    public static void message (Context context, String message) {
        Toast toast = Toast.makeText(context,
                message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
